package cn.hy.controller;

import cn.hy.base.BaseController;
import cn.hy.po.Item;
import cn.hy.po.ItemCategory;
import cn.hy.service.ItemCategoryService;
import cn.hy.service.ItemService;
import cn.hy.utils.Pager;
import cn.hy.utils.SystemContext;
import cn.hy.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/item")
public class ItemController extends BaseController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemCategoryService itemCategoryService;
    /**
     * 分页查询列表
     * @param model
     * @param item
     * @return
     */
    @RequestMapping("/findBySql")
    public String findBySql(Model model, Item item){
        String sql = "select * from item where isDelete = 0 ";
        if(!isEmpty(item.getName())){
            sql += " and name like '%" + item.getName() + "%' ";
        }
        sql += " order by id desc";
        Pager<Item> pagers = itemService.findBySqlRerturnEntity(sql);
        model.addAttribute("pagers",pagers);
        model.addAttribute("obj",item);
        return "item/item";
    }

    /**
     * 添加商品,并在add页面返回商品类别
     * @param model
     * @return
     */
    @RequestMapping("/add")
    public String add(Model model){
        String sql="select * from item_category where isDelete = 0 and pid is not null order by id";
        List<ItemCategory> itemCategoryList=itemCategoryService.listBySqlReturnEntity(sql);
        model.addAttribute("types",itemCategoryList);
        return "item/add";
    }

    /**
     * 添加商品详细信息  含图片上传
     * @param item
     * @param files
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/exAdd")
    public String exAdd(Item item, @RequestParam("file") CommonsMultipartFile[] files, HttpServletRequest request) throws IOException {
        if(files.length>0) {
            for (int s = 0; s < files.length; s++) {
                String n = UUIDUtils.create();
                String path = SystemContext.getRealPath() + "\\resource\\ueditor\\upload\\" + n + files[s].getOriginalFilename();
                File newFile = new File(path);
                //通过CommonsMultipartFile的方法直接写文件
                files[s].transferTo(newFile);
                if (s == 0) {
                    item.setUrl1(request.getContextPath()+"\\resource\\ueditor\\upload\\" + n + files[s].getOriginalFilename());
                }
                if (s == 1) {
                    item.setUrl2(request.getContextPath()+"\\resource\\ueditor\\upload\\" + n + files[s].getOriginalFilename());
                }
                if (s == 2) {
                    item.setUrl3(request.getContextPath()+"\\resource\\ueditor\\upload\\" + n + files[s].getOriginalFilename());
                }
                if (s == 3) {
                    item.setUrl4(request.getContextPath()+"\\resource\\ueditor\\upload\\" + n + files[s].getOriginalFilename());
                }
                if (s == 4) {
                    item.setUrl5(request.getContextPath()+"\\resource\\ueditor\\upload\\" + n + files[s].getOriginalFilename());
                }
            }
        }
        item.setGmNum(0);
        item.setIsDelete(0);
        item.setScNum(0);
        ItemCategory byId=itemCategoryService.getById(item.getCategoryIdTwo());
        item.setCategoryIdOne(byId.getPid());
//        System.err.println(item.getMs());
        itemService.insert(item);
        return "redirect:/item/findBySql";
    }

    /**
     * 修改商品入口
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/update")
    public String update(Integer id,Model model){
        Item obj=itemService.load(id);
        String sql="select * from item_category where isDelete = 0 and pid is not null order by id";
        List<ItemCategory> itemCategoryList=itemCategoryService.listBySqlReturnEntity(sql);
        model.addAttribute("types",itemCategoryList);
        model.addAttribute("obj",obj);
        return "item/update";
    }

    /**
     * 执行修改商品页面
     * @param item
     * @param files
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/exUpdate")
    public String exUpdate(Item item, @RequestParam("file")CommonsMultipartFile[] files, HttpServletRequest request) throws IOException {
        itemCommon(item, files, request);
        itemService.updateById(item);
        return "redirect:/item/findBySql";
    }


    /**
     * 抽取出共同的代码
     * @param item
     * @param files
     * @param request
     * @throws IOException
     */
    private void itemCommon(Item item, @RequestParam("file") CommonsMultipartFile[] files, HttpServletRequest request) throws IOException {
        if(files.length>0) {
            for (int s = 0; s < files.length; s++) {
                String n = UUIDUtils.create();
                String path = SystemContext.getRealPath() + "\\resource\\ueditor\\upload\\" + n + files[s].getOriginalFilename();
                File newFile = new File(path);
                //通过CommonsMultipartFile的方法直接写文件
                files[s].transferTo(newFile);
                if (s == 0) {
                    item.setUrl1(request.getContextPath()+"\\resource\\ueditor\\upload\\" + n + files[s].getOriginalFilename());
                }
                if (s == 1) {
                    item.setUrl2(request.getContextPath()+"\\resource\\ueditor\\upload\\" + n + files[s].getOriginalFilename());
                }
                if (s == 2) {
                    item.setUrl3(request.getContextPath()+"\\resource\\ueditor\\upload\\" + n + files[s].getOriginalFilename());
                }
                if (s == 3) {
                    item.setUrl4(request.getContextPath()+"\\resource\\ueditor\\upload\\" + n + files[s].getOriginalFilename());
                }
                if (s == 4) {
                    item.setUrl5(request.getContextPath()+"\\resource\\ueditor\\upload\\" + n + files[s].getOriginalFilename());
                }
            }
        }
        ItemCategory byId=itemCategoryService.getById(item.getCategoryIdTwo());
        item.setCategoryIdOne(byId.getPid());
    }
    /**
     * 下架商品
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public String delete(Integer id){
        Item obj=itemService.load(id);
        obj.setIsDelete(1);
        itemService.updateById(obj);
        return "redirect:/item/findBySql";
    }

    /**
     * 按关键词或二级分类查询
     * @param item
     * @param condition
     * @param model
     * @return
     */
    @RequestMapping("/shoplist")
    public String shoplist(Item item,String condition,Model model){
        String sql="select * from item where isDelete=0";
        if(!isEmpty(item.getCategoryIdTwo())){
            sql +=" and category_id_two = "+item.getCategoryIdTwo();
        }
        if(!isEmpty(condition)){
            sql += " and name like '%" + condition +"%' ";
            model.addAttribute("condition",condition);
        }
        //按价格排序 由高到低 varchar+0 -> int
        if(!isEmpty(item.getPrice())){
            sql+=" order by (price+0) desc";
        }
        if(!isEmpty(item.getGmNum())){
            sql += " order by gmNum desc";
        }
        //默认
        if(isEmpty(item.getPrice()) && isEmpty(item.getGmNum())){
            sql +=" order by id desc";
        }
        Pager<Item> pagers=itemService.findBySqlRerturnEntity(sql);
        model.addAttribute("pagers",pagers);
        model.addAttribute("obj",item);
        return "item/shoplist";
    }

    /**
     * 商品详情
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/view")
    public String view(Integer id,Model model){
        Item item=itemService.load(id);
        model.addAttribute("obj",item);
        return "item/view";
    }
}
