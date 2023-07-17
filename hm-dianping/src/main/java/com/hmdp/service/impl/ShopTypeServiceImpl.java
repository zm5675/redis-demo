package com.hmdp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hmdp.dto.Result;
import com.hmdp.entity.ShopType;
import com.hmdp.mapper.ShopTypeMapper;
import com.hmdp.service.IShopTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.hmdp.utils.RedisConstants.CACHE_SHOP_TYPE;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@Service
public class ShopTypeServiceImpl extends ServiceImpl<ShopTypeMapper, ShopType> implements IShopTypeService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public Result queryTypeList() {
        // 判断缓存中是否有这个店铺类别
        String shopListJson = stringRedisTemplate.opsForValue().get(CACHE_SHOP_TYPE);
        // 当缓存中有这个数据
        if (StringUtils.isNotBlank(shopListJson)){
            List<ShopType> shopTypes = JSONUtil.toList(shopListJson, ShopType.class);
            return Result.ok(shopTypes) ;
        }
        List<ShopType> list = query().orderByAsc("sort").list();
        if (list.size() > 0){
            String jsonStr = JSONUtil.toJsonStr(list);
            stringRedisTemplate.opsForValue().set(CACHE_SHOP_TYPE,jsonStr);
            return Result.ok(list);
        }
        return Result.fail("商品类别为空");

    }
}
