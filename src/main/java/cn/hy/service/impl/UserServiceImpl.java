package cn.hy.service.impl;

import cn.hy.base.BaseDao;
import cn.hy.base.BaseServiceImpl;
import cn.hy.mapper.UserMapper;
import cn.hy.po.User;
import cn.hy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Autowired
    UserMapper userMapper;
    @Override
    public BaseDao<User> getBaseDao() {
        return userMapper;
    }
}
