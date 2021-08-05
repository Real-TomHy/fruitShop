package cn.hy.controller;

import cn.hy.base.BaseController;
import cn.hy.po.Message;
import cn.hy.po.User;
import cn.hy.service.MessageService;
import cn.hy.utils.Pager;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 留言管理
 */
@Controller
@RequestMapping("/message")
public class MessageController extends BaseController {

    @Autowired
    private MessageService messageService;

    /**
     * 分页查询
     * @param message
     * @param model
     * @return
     */
    @RequestMapping("/findBySql")
    public String findBySql(Message message, Model model){
        String sql = "select * from message where 1=1 ";
        if(!isEmpty(message.getName())){
            sql += " and name like '%"+message.getName()+"%' ";
        }
        sql+=" order by id";
        Pager<Message> pagers = messageService.findBySqlRerturnEntity(sql);
        model.addAttribute("pagers",pagers);
        model.addAttribute("obj",message);
        return "message/message";
    }
    /**
     * 删除
     */
    @RequestMapping("delete")
    public String delete(Integer id){
        messageService.deleteById(id);
        return "redirect:/message/findBySql";
    }

    /**
     * 跳转发布留言
     * @return
     */
    @RequestMapping("/add")
    public String add(){
        return "message/add";
    }
    @RequestMapping("/exAdd")
    @ResponseBody
    public String exAdd(Message message){
        messageService.insert(message);
        JSONObject js = new JSONObject();
        js.put("message","添加成功");
        return js.toString();
    }
}
