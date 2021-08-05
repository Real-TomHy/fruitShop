package cn.hy.service.impl;

import cn.hy.base.BaseDao;
import cn.hy.base.BaseServiceImpl;
import cn.hy.mapper.OrderDetailMapper;
import cn.hy.po.OrderDetail;
import cn.hy.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends BaseServiceImpl<OrderDetail> implements OrderDetailService{
    @Autowired
    OrderDetailMapper orderDetailMapper;

    @Override
    public BaseDao<OrderDetail> getBaseDao() {
        return orderDetailMapper;
    }
}
