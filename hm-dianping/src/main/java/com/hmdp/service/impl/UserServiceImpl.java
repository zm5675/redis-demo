package com.hmdp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.dto.LoginFormDTO;
import com.hmdp.dto.Result;
import com.hmdp.dto.UserDTO;
import com.hmdp.entity.User;
import com.hmdp.mapper.UserMapper;
import com.hmdp.service.IUserService;
import com.hmdp.utils.RegexUtils;
import com.hmdp.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.hmdp.utils.RedisConstants.*;
import static com.hmdp.utils.SystemConstants.USER_NICK_NAME_PREFIX;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result sendCode(String phone, HttpSession session) {
        // 验证手机号
        if (RegexUtils.isPhoneInvalid(phone)) {
            // 如果不符合就返回错误信息
            return Result.fail("手机号格式错误!");
        }
        // 符合，生成验证码
        String code = RandomUtil.randomNumbers(6);
        // 保存验证码到redis中,并设置验证码有效期 set key value ex 120s
        stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY + phone, code,2, TimeUnit.MINUTES);
        log.debug("短信验证码发送成功，验证码是{}", code);
        // 返回ok
        return Result.ok();
    }

    @Override
    public Result userLogin(LoginFormDTO loginForm, HttpSession session) {
        // 验证手机号
        String phone = loginForm.getPhone();
        String code = loginForm.getCode();
        if (RegexUtils.isPhoneInvalid(phone)) {
            // 如果不符合就返回错误信息
            return Result.fail("手机号格式错误!");
        }
        // 校验验证码,从redis中获取
//        Object catchCode = session.getAttribute("code");
        String catchCode = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY + phone);
        if (catchCode == null || !catchCode.toString().equals(code)) {
            // 验证码不存在或不一致
            return Result.fail("验证码错误！");
        }
        LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(StringUtils.isNotBlank(phone), User::getPhone, phone);
        User user = getOne(lambdaUpdateWrapper);
        if (user == null) {
            // 用户不存在，创建新用户并保存
            user = createUserWithPhone(phone);
        }
        // 生成随机token，作为登入令牌
        String token = UUID.randomUUID().toString(true);
        // 将User对象保存到Hash存储
        UserDTO userDTO = BeanUtil.copyProperties(user,UserDTO.class);
//        BeanUtil.beanToMap(userDTO,new HashMap<String, String>(),
//                                    CopyOptions.create().
//                                            setIgnoreNullValue(true).
//                                            setFieldNameEditor(fieldName ->
//                                                                    fieldName.toString()
//                                                                ));
        Map<String,String> userMap = new HashMap<>();
        userMap.put("id",user.getId().toString());
        userMap.put("nickName",user.getNickName());
        if (user.getIcon() != null){
            userMap.put("icon",user.getIcon());
        }
        // 将用户信息保存到redis中
        String tokenKey = LOGIN_USER_KEY + token;
        stringRedisTemplate.opsForHash().putAll(tokenKey,userMap);
        // 设置存活时间
        stringRedisTemplate.expire(tokenKey,30,TimeUnit.MINUTES);
        // 将toke返回客户端
        //session.setAttribute("user", BeanUtil.copyProperties(user, UserDTO.class));
        return Result.ok(token);

    }

    /**
     * 签到功能
     */
    @Override
    public Result sign() {
        // 获取当前登入用户
        Long userId = UserHolder.getUser().getId();
        // 获取日期
        LocalDateTime now = LocalDateTime.now();
        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        // 拼接key
        String key =USER_SIGN_KEY +  userId + keySuffix;
        // 获取当天是本月的第几天
        int dayOfMonth = now.getDayOfMonth();
        // 写入redis中 SETBIT key offset 1
        stringRedisTemplate.opsForValue().setBit(key,dayOfMonth - 1,true);
        return Result.ok();
    }

    /**
     * 获取本月的连续签到次数
     * @return
     */
    @Override
    public Result signCount() {
        // 获取当前登入用户
        Long userId = UserHolder.getUser().getId();
        // 获取日期
        LocalDateTime now = LocalDateTime.now();
        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        // 拼接key
        String key =USER_SIGN_KEY +  userId + keySuffix;
        // 获取当天是本月的第几天
        int dayOfMonth = now.getDayOfMonth();
        // 获取本月的签到次数，从redis中获取数据例：BITFIELD sign:5:202303 GET u14 0
        List<Long> res = stringRedisTemplate.opsForValue().bitField(key,
                BitFieldSubCommands.create()
                        .get(BitFieldSubCommands.BitFieldType.unsigned(dayOfMonth))
                        .valueAt(0));
        if (res == null || res.isEmpty()) {
            return Result.ok(0);
        }
        // 继续判断，res中只有一个结果
        Long num = res.get(0);
        if (num == null || num == 0){
            return Result.ok(0);
        }
        int count = 0;
        // 循环遍历
        while (true){
            // 让这个数字与1做与运算，得到数字的最后一个bit位，判断这个bit位是否为0
            if ((num & 1) == 0){
                break;
            }else {
                // 如果不为0，计数器加一
                count++;
            }
            num >>>= 1;
        }
        return Result.ok(count);
    }

    private User createUserWithPhone(String phone) {
        User user = new User();
        user.setPhone(phone);
        user.setNickName(USER_NICK_NAME_PREFIX + RandomUtil.randomString(6));
        boolean save = save(user);
        System.out.println(save);
        return user;
    }
}
