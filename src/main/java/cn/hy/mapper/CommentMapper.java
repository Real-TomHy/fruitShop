package cn.hy.mapper;

import cn.hy.base.BaseDao;
import cn.hy.po.Comment;

import java.io.Serializable;
import java.util.List;

public interface CommentMapper extends BaseDao<Comment> {
    List<Comment> listByItemId(Serializable id);
}
