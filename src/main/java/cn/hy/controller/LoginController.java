package cn.hy.controller;

import cn.hy.base.BaseController;
import cn.hy.po.*;
import cn.hy.service.ItemCategoryService;
import cn.hy.service.ItemService;
import cn.hy.service.ManageService;
import cn.hy.service.UserService;
import cn.hy.utils.Consts;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 登录
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {
    @Autowired
    private UserService userService;

    @Autowired
    ManageService manageService;
    @Autowired
    private ItemCategoryService itemCategoryService;
    @Autowired
    private ItemService itemService;
    /**
     * 管理员登录前
     * @return
     */
    @RequestMapping("login")
    public  String login(){
        return "/login/mLogin";
    }

    /**
     * 登录验证
     * @return
     */
    @RequestMapping("toLogin")
    public  String toLogin(Manage manage, HttpServletRequest request){
        Manage byEntity=manageService.getByEntity(manage);
        //判断是否存在
        if(null==byEntity){
            return "redirect:/login/mQuit";
        }
        request.getSession().setAttribute(Consts.MANAGE,byEntity);
        return "/login/mIndex";
    }
    /**
     * 管理员退出
     */
    @RequestMapping("mQuit")
    public String mtuichu(HttpServletRequest request){
        request.getSession().setAttribute(Consts.MANAGE,null);
        return "/login/mLogin";
    }
    /**
     * 首页
     */
    @RequestMapping("/uIndex")
    public String uIndex(Model model, Item item,HttpServletRequest request){
        String sql="select * from item_category where isDelete=0 and pid is null order by name";
        List<ItemCategory> fatherList=itemCategoryService.listBySqlReturnEntity(sql);
        List<CategoryDto> categoryDtoList=new ArrayList<>();
        if(!CollectionUtils.isEmpty(fatherList)){
            for(ItemCategory ic:fatherList){
                CategoryDto categoryDto=new CategoryDto();
                categoryDto.setFather(ic);
                //查询二级分类,子水果的pid是父水果的id
                String sql2="select * from item_category where isDelete=0 and pid="+ic.getId();
                List<ItemCategory> childrensList=itemCategoryService.listBySqlReturnEntity(sql2);
                categoryDto.setChildrens(childrensList);
                categoryDtoList.add(categoryDto);
                model.addAttribute("lbs",categoryDtoList);
            }
        }
        //促销商品
        String sql3="select * from item where isDelete=0 and zk is not null order by zk desc limit 0,10";
        List<Item> zkxItems=itemService.listBySqlReturnEntity(sql3);
        model.addAttribute("zks",zkxItems);

        //热门商品
        String sql4="select * from item where isDelete=0 order by gmNum desc limit 0,10";
        List<Item> rxsItems=itemService.listBySqlReturnEntity(sql4);
        model.addAttribute("rxs",rxsItems);
        return "login/uIndex";
    }
    /**
     * 跳转到普通用户注册
     */
    @RequestMapping("/res")
    public String res(){
        return "login/res";
    }

    /**
     * 跳转到登录页面
     * @return
     */
    @RequestMapping("/uLogin")
    public String uLogin(){
        return "login/uLogin";
    }
    /**
     * 处理普通用户注册
     */
    @RequestMapping("/toRes")
    public String toRes(User user){
        userService.insert(user);
        //跳转到登录页面
        return "redirect:/login/uLogin";
    }

    /**
     * 处理普通用户登录
     * @param user
     * @return
     */
    @RequestMapping("/utoLogin")
    public String utoLogin(User user,HttpServletRequest request,Model model){
        User byEntity= userService.getByEntity(user);
        //判断是否存在
        if(null==byEntity){
            model.addAttribute("message","密码或账号错误");
            return "login/uLogin";
        }
        //放入域中
        request.getSession().setAttribute("role",2);
       // Consts定义一个常量 ，可以避免使用"username"
        request.getSession().setAttribute(Consts.USERNAME,byEntity.getUserName());
        request.getSession().setAttribute(Consts.USERID,byEntity.getId());
        return "redirect:/login/uIndex";
    }

    /**
     * 用户退出
     * @param request
     * @return
     */
    @RequestMapping("/uQuit")
    public String uQuit(HttpServletRequest request){
        HttpSession session=request.getSession();
        session.invalidate();
        return "redirect:/login/uIndex";
    }

    /**
     * 跳转到修改密码
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/pass")
    public String pass(HttpServletRequest request,Model model){
        Object attribute = request.getSession().getAttribute(Consts.USERID);
        if(attribute==null){
            return "redirect:/login/uLogin";
        }
        Integer userId = Integer.valueOf(attribute.toString());
        User u=userService.load(userId);
        model.addAttribute("obj",u);
        return "login/pass";
    }

    /**
     * 修改普通用户密码
     * @param
     * @param request
     * @return
     */
    @RequestMapping("/upass")
    @ResponseBody
    public String upass(String password,HttpServletRequest request){
        JSONObject js = new JSONObject();
        Object attribute = request.getSession().getAttribute(Consts.USERID);
        if(attribute==null){
            js.put(Consts.RES,0);
            return js.toString();
        }
        Integer userId = Integer.valueOf(attribute.toString());
        User load = userService.load(userId);
        load.setPassWord(password);
        userService.updateById(load);
        js.put(Consts.RES,1);
        return js.toString();
    }

}
