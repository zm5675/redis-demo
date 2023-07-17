package com.sa.mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sa.mybatisplus.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<User> {
    List<User> selectByEmail(String Email);

    int delByIdAndEmailAndEmail(@Param("id") Long id, @Param("email") String email, @Param("oldEmail") String oldEmail);
}
