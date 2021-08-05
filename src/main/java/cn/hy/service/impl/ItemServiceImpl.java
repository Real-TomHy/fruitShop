package cn.hy.service.impl;

import cn.hy.base.BaseDao;
import cn.hy.base.BaseServiceImpl;
import cn.hy.mapper.ItemMapper;
import cn.hy.po.Item;
import cn.hy.service.ItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService {
    @Autowired
    ItemMapper itemMapper;

    @Override
    public BaseDao<Item> getBaseDao() {
        return itemMapper;
    }
}
