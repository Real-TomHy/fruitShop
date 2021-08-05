package cn.hy.service.impl;

import cn.hy.base.BaseDao;
import cn.hy.base.BaseServiceImpl;
import cn.hy.mapper.MessageMapper;
import cn.hy.po.Message;
import cn.hy.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl extends BaseServiceImpl<Message> implements MessageService {

    @Autowired
    MessageMapper messageMapper;
    @Override
    public BaseDao<Message> getBaseDao() {
        return messageMapper;
    }
}
