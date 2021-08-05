package cn.hy.service.impl;


import cn.hy.base.BaseDao;
import cn.hy.base.BaseServiceImpl;
import cn.hy.mapper.ScMapper;
import cn.hy.po.Sc;
import cn.hy.service.ScService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScServiceImpl extends BaseServiceImpl<Sc> implements ScService {
    @Autowired
    ScMapper scMapper;
    @Override
    public BaseDao<Sc> getBaseDao() {
        return scMapper;
    }
}
