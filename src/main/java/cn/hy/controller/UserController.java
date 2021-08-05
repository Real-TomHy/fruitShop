package cn.hy.controller;

import cn.hy.base.BaseController;
import cn.hy.po.User;
import cn.hy.service.UserService;
import cn.hy.utils.Consts;
import cn.hy.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户c层
 */
@Controller
@RequestMapping("/user")
public class UserController  extends BaseController {
    @Autowired
    private UserService userService;

    @RequestMapping("/findBySql")
    public String findBySql(Model model,User user){
        String sql = "select * from user where 1=1 ";
        if(!isEmpty(user.getUserName())){
            sql += " and userName like '%"+user.getUserName()+"%' ";
        }
        sql+=" order by id";
        Pager<User> pagers = userService.findBySqlRerturnEntity(sql);
        model.addAttribute("pagers",pagers);
        model.addAttribute("obj",user);
        return "user/user";
    }

    /**
     * 跳转到个人页面
     * @param request
     * @return
     */
    @RequestMapping("/view")
    public String view(HttpServletRequest request,Model model){
        Object attribute = request.getSession().getAttribute(Consts.USERID);
        if(attribute==null){
            return "redirect:/login/uLogin";
        }
        Integer userId = Integer.valueOf(attribute.toString());
        User u=userService.load(userId);
        model.addAttribute("obj",u);
        return "user/view";
    }

    /**
     * 保存(修改)用户业务
     * @param user
     * @param request
     * @return
     */
    @RequestMapping("/exUpdate")
    public String exUpdate(User user,HttpServletRequest request){
        Object attribute = request.getSession().getAttribute(Consts.USERID);
        if(attribute==null){
            return "redirect:/login/uLogin";
        }
        Integer userId = Integer.valueOf(attribute.toString());
        user.setId(userId);
        userService.updateById(user);
        return "redirect:/user/view";


    }
}
