package cn.hy.controller;

import cn.hy.base.BaseController;
import cn.hy.po.Item;
import cn.hy.po.Sc;
import cn.hy.service.ItemService;
import cn.hy.service.ScService;
import cn.hy.utils.Consts;
import cn.hy.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 收藏
 */
@Controller
@RequestMapping("/sc")
public class ScController extends BaseController {
    @Autowired
    private ScService scService;
    @Autowired
    private ItemService itemService;
    @RequestMapping("/exAdd")
    //只传了一个itemId,但是可以用Sc接受
    public String exAdd(HttpServletRequest request, Sc sc){

        Object attribute = request.getSession().getAttribute(Consts.USERID);
        if(attribute==null){
            return "redirect:/login/uLogin";
        }
        Integer userId = Integer.valueOf(attribute.toString());
        sc.setUserId(userId);
        scService.insert(sc);
        //商品表收藏数+1
        Item item = itemService.load(sc.getItemId());
        item.setScNum(item.getScNum()+1);
        itemService.updateById(item);
        return "redirect:/sc/findBySql";
    }

    /**
     * 商品列表
     * @return
     */
    @RequestMapping("/findBySql")
    public String findBySql(HttpServletRequest request, Model model){
        Object attribute = request.getSession().getAttribute(Consts.USERID);
        if(attribute==null){
            return "redirect:/login/uLogin";
        }
        Integer userId = Integer.valueOf(attribute.toString());
        String sql="select * from sc where user_id="+userId+" order by id desc";
        //联合查询item
        Pager<Sc> pagers = scService.findBySqlRerturnEntity(sql);
        model.addAttribute("pagers",pagers);
        return "sc/my";
    }

    @RequestMapping("/delete")
    //传过来的是id 但是可以用Sc接收
    public String delete(Sc sc,HttpServletRequest request){
        Object attribute = request.getSession().getAttribute(Consts.USERID);
        if(attribute==null){
            return "redirect:/login/uLogin";
        }
        scService.deleteByEntity(sc);
        return "redirect:/sc/findBySql";
    }
}
