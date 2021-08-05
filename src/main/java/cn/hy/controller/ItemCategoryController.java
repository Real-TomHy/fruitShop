package cn.hy.controller;

import cn.hy.base.BaseController;
import cn.hy.po.ItemCategory;
import cn.hy.service.ItemCategoryService;
import cn.hy.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 类目c层
 */
@Controller
@RequestMapping("/itemCategory")
public class ItemCategoryController extends BaseController {

    @Autowired
    private ItemCategoryService itemCategoryService;

    /**
     * 分页查询类目列表
     */
    @RequestMapping("/findBySql")
    public String findBySql(Model model, ItemCategory itemCategory){
        String sql = "select * from item_category where isDelete = 0 and pid is null order by id";
        Pager<ItemCategory> pagers = itemCategoryService.findBySqlRerturnEntity(sql);
        model.addAttribute("pagers",pagers);
        model.addAttribute("obj",itemCategory);
        return "itemCategory/itemCategory";
    }

    /**
     * 转向到新增一级分类页面
     */
    @RequestMapping(value = "/add")
    public String add(){
        return "itemCategory/add";
    }

    /**
     * 新增一级分类保持功能
     * @param itemCategory
     * @return
     */
    @RequestMapping("/exAdd")
    public String exAdd(ItemCategory itemCategory){
        itemCategory.setIsDelete(0);
        itemCategoryService.insert(itemCategory);
//        return "redirect/itemCategory/findBySql.action";
        return "redirect:/itemCategory/findBySql";
    }

    /**
     * 转向修改一级分类页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/update")
    public String update(Integer id,Model model){
        ItemCategory obj=itemCategoryService.load(id);
        model.addAttribute("obj",obj);
        return "itemCategory/update";
    }

    /**
     * 修改一级分类
     * @param itemCategory
     * @return
     */
    @RequestMapping("/exUpdate")
    public String exUpdate(ItemCategory itemCategory){
        itemCategoryService.updateById(itemCategory);
        return "redirect:/itemCategory/findBySql";

    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public String delete(Integer id){
        ItemCategory itemCategory=itemCategoryService.load(id);
        itemCategory.setIsDelete(1);
        itemCategoryService.updateById(itemCategory);
        //将下级也删除
        String sql="update item_category set isDelete=1 where pid="+id;
        itemCategoryService.updateBysql(sql);
        return "redirect:/itemCategory/findBySql";
    }

    /**
     * 查看二级分类
     * @param itemCategory  虽然前端传来的是int，但是可以用itemCategory封装
     * @param model
     * @return
     */
    @RequestMapping("/findBySql2")
    public String findBySql2(ItemCategory itemCategory,Model model){
        String sql="select * from item_category where isDelete=0 and pid="+itemCategory.getPid()+" order by id";
        Pager<ItemCategory> pagers=itemCategoryService.findBySqlRerturnEntity(sql);
        model.addAttribute("pagers",pagers);
        model.addAttribute("obj",itemCategory);
        return "itemCategory/itemCategory2";
    }

    /**
     * 转向到新增二级分类页面
     */
    @RequestMapping(value = "/add2")
    public String add2(int pid,Model model){
        model.addAttribute("pid",pid);
        return "itemCategory/add2";
    }

    /**
     * 新增二级类目保存功能
     * @param itemCategory
     * @return
     */
    @RequestMapping("/exAdd2")
    public String exAdd2(ItemCategory itemCategory){
        itemCategory.setIsDelete(0);
        itemCategoryService.insert(itemCategory);
        return "redirect:/itemCategory/findBySql2?pid="+itemCategory.getPid();
    }


    /**
     * 转向修改二级分类页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/update2")
    public String update2(Integer id,Model model){
        ItemCategory obj=itemCategoryService.load(id);
        model.addAttribute("obj",obj);
        return "itemCategory/update2";
    }

    /**
     * 修改二级分类
     * @param itemCategory
     * @return
     */
    @RequestMapping("/exUpdate2")
    public String exUpdate2(ItemCategory itemCategory){
        itemCategoryService.updateById(itemCategory);
        return "redirect:/itemCategory/findBySql2?pid="+itemCategory.getPid();

    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("/delete2")
    public String delete2(Integer id){
        ItemCategory itemCategory=itemCategoryService.load(id);
        itemCategory.setIsDelete(1);
        itemCategoryService.updateById(itemCategory);
        //将下级也删除
        String sql="update item_category set isDelete=1 where pid="+id;
        itemCategoryService.updateBysql(sql);
        return "redirect:/itemCategory/findBySql2?pid="+itemCategory.getPid();
    }

}
