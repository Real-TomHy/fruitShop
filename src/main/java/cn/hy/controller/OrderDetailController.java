package cn.hy.controller;


import cn.hy.po.OrderDetail;
import cn.hy.service.OrderDetailService;
import cn.hy.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 订单详情
 */
@Controller
@RequestMapping("/orderDetail")
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;

    /**
     * 分页
     * @param orderDetail
     * @param model
     * @return
     */
    @RequestMapping("/ulist")
    public String ulist(OrderDetail orderDetail, Model model){
        //分页
        String sql="select * from order_detail where order_id="+orderDetail.getOrderId();
        Pager<OrderDetail> pagers=orderDetailService.findBySqlRerturnEntity(sql);
        model.addAttribute("pagers",pagers);
        model.addAttribute("obj",orderDetail);
        return "orderDetail/ulist";
    }

}
