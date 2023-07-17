package com.sa.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sa.mapper.UserMapper;
import com.sa.mybatisplus.pojo.User;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class MyBatisTest {
    @Autowired
    private UserMapper userMapper;
    @Test
    public void testSelectList(){
        List<User> users = userMapper.selectList(null);
        users.stream().forEach(System.out::println);
    }
    @Test
    public void selectPage(){
        Page<User> page = new Page<>(1,2);
        userMapper.selectPage(page,null);
        System.out.println(page.getRecords());

        System.out.println(page);

    }
    @Test
    public void testSelectListQueryWapper(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank("jab"),"email","abc");
        List<User> users = userMapper.selectList(null);
        users.stream().forEach(System.out::println);
    }

    @Test
    public void testSelectListByEmail(){
        List<User> users = userMapper.selectByEmail("test5@baomidou.com");
        users.stream().forEach(System.out::println);
    }

    @Test
    public void testInsert(){
       User user = new User();
       user.setAge(13);
       user.setName("sa");
       user.setEmail("9123489@aa.com");
       userMapper.insert(user);
        System.out.println(user);
    }

    @Test
    public void testDeleteByMap(){
        Map<String,Object> map = new HashMap<>();
        map.put("name","sa");
        map.put("age",13);
        int i = userMapper.deleteByMap(map);
        System.out.println(i);
    }

    @Test
    public void testDeleteByList(){
        List<Object> list = Arrays.asList(4L,5L);
        int i = userMapper.deleteBatchIds(list);
        System.out.println(i);
    }
    @Test
    public void testLambadQueryWapper(){
        LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(true,User::getAge,18);
        List<User> users = userMapper.selectList(null);
        users.stream().forEach(System.out::println);
    }
}
