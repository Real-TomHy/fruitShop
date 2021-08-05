package cn.hy.mapper;

import cn.hy.base.BaseDao;
import cn.hy.po.Comment;
import cn.hy.po.OrderDetail;

import java.io.Serializable;
import java.util.List;

public interface OrderDetailMapper extends BaseDao<OrderDetail> {
    List<OrderDetail> listByOrderId(Serializable id);
}
