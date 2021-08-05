package cn.hy.service.impl;

import cn.hy.base.BaseDao;
import cn.hy.base.BaseServiceImpl;
import cn.hy.mapper.ManageMapper;
import cn.hy.po.Manage;
import cn.hy.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManageServiceImpl extends BaseServiceImpl<Manage> implements ManageService {
    @Autowired
    ManageMapper manageMapper;
    @Override
    public BaseDao<Manage> getBaseDao() {
        return manageMapper;
    }
}
