package com.sa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sa.mybatisplus.domain.Car;
import com.sa.service.CarService;
import com.sa.mapper.CarMapper;
import org.springframework.stereotype.Service;

/**
* @author 张鑫
* @description 针对表【t_car】的数据库操作Service实现
* @createDate 2023-04-12 09:36:32
*/
@Service
public class CarServiceImpl extends ServiceImpl<CarMapper, Car>
    implements CarService{

}




