package cn.hy.controller;

import cn.hy.base.BaseController;
import cn.hy.po.Car;
import cn.hy.po.Item;
import cn.hy.po.Sc;
import cn.hy.service.CarService;
import cn.hy.service.ItemCategoryService;
import cn.hy.service.ItemService;
import cn.hy.utils.Consts;
import cn.hy.utils.Pager;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 购物车
 */
@Controller
@RequestMapping("/car")
public class CarController extends BaseController {
    @Autowired
    private ItemService itemService;

    @Autowired
    private CarService carService;

    /**
     * 加入购物车
     * @param request
     * @param car
     * @return
     */
    @RequestMapping("/exAdd")
    @ResponseBody
    public String exAdd(HttpServletRequest request, Car car){
        JSONObject js = new JSONObject();
        Object attribute = request.getSession().getAttribute(Consts.USERID);
        if(attribute==null){
            js.put(Consts.RES,0);
            return js.toJSONString();
        }
        Integer userId = Integer.valueOf(attribute.toString());
        car.setUserId(userId);
        //根据itemId查询item
        Item item = itemService.load(car.getItemId());
        String price = item.getPrice();
        Double valueOf = Double.valueOf(price);
        car.setPrice(valueOf);
        //计算打折
        if(item.getZk()!=null){
            valueOf = valueOf*item.getZk()/10;
            BigDecimal bg = new BigDecimal(valueOf).setScale(2, RoundingMode.UP);
            car.setPrice(bg.doubleValue());
            valueOf = bg.doubleValue();
        }
        Integer num = car.getNum();
        Double t = valueOf*num;
        BigDecimal bg = new BigDecimal(t).setScale(2, RoundingMode.UP);
        double doubleValue = bg.doubleValue();
        car.setTotal(doubleValue+"");
        carService.insert(car);
        js.put(Consts.RES,1);
        return js.toJSONString();
    }

    /**
     * 转向个人购物车页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/findBySql")
    public String findBySql(HttpServletRequest request, Model model){
        Object attribute = request.getSession().getAttribute(Consts.USERID);
        if(attribute==null){
            return "redirect:/login/uLogin";
        }
        Integer userId = Integer.valueOf(attribute.toString());
        String sql="select * from car where user_id="+userId+" order by id desc";
        //联合查询item
        List<Car> list = carService.listBySqlReturnEntity(sql);
        model.addAttribute("list",list);
        return "car/car";
    }

    /**
     * 删除
     * @param id
     * @return
     */
    // ResponseBody注解作用：将方法的返回值，以特定的格式写入到response的body区域，进而将数据返回给客户端。
    @RequestMapping("/delete")
    @ResponseBody
    public String delete(Integer id){
        carService.deleteById(id);
        return "success";
    }
}
