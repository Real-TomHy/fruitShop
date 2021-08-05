package cn.hy.service.impl;

import cn.hy.base.BaseDao;
import cn.hy.base.BaseServiceImpl;
import cn.hy.mapper.CarMapper;
import cn.hy.po.Car;
import cn.hy.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl extends BaseServiceImpl<Car> implements CarService {
    @Autowired
    CarMapper carMapper;
    @Override
    public BaseDao<Car> getBaseDao() {
        return carMapper;
    }
}
