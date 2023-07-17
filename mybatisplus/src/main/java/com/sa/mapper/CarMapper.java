package com.sa.mapper;

import com.sa.mybatisplus.domain.Car;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 张鑫
* @description 针对表【t_car】的数据库操作Mapper
* @createDate 2023-04-12 09:36:32
* @Entity com.sa.mybatisplus.domain.Car
*/
public interface CarMapper extends BaseMapper<Car> {
    int insertSelective(Car car);

    int insertAll(Car car);

}




