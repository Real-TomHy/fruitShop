package cn.hy.service.impl;

import cn.hy.base.BaseDao;
import cn.hy.base.BaseServiceImpl;
import cn.hy.mapper.ItemOrderMapper;
import cn.hy.po.ItemOrder;
import cn.hy.service.ItemOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemOrderServiceImpl extends BaseServiceImpl<ItemOrder> implements ItemOrderService {
    @Autowired
    ItemOrderMapper itemOrderMapper;

    @Override
    public BaseDao<ItemOrder> getBaseDao() {
        return itemOrderMapper;
    }
}
