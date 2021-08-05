package cn.hy.controller;

import cn.hy.base.BaseController;
import cn.hy.po.News;
import cn.hy.service.NewsService;
import cn.hy.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * 公告管理
 */
@Controller
@RequestMapping("/news")
public class NewsController extends BaseController {

    @Autowired
    private NewsService newsService;

    @RequestMapping("/findBySql")
    public String findBySql(News news, Model model){
        String sql="select * from news where 1=1";
        if(!isEmpty(news.getName())){
            sql += " and name like '%"+news.getName()+"%'";
        }
        sql += " order by id desc";
        Pager<News> pagers=newsService.findBySqlRerturnEntity(sql);
        model.addAttribute("pagers",pagers);
        model.addAttribute("obj",news);
        return "news/news";
    }
    /**
     * 跳转添加页面
     */
    @RequestMapping("/add")
    public String add(){

        return "news/add";
    }
    /**
     * 添加执行
     */
    @RequestMapping("/exAdd")
    public String exAdd(News news){
        news.setAddTime((new Date()));
        newsService.insert(news);
        return "redirect:/news/findBySql";
    }

    /**
     * 跳转修改页面
     */
    @RequestMapping("/update")
    public String update(Integer id,Model model){
        News obj=newsService.load(id);
        model.addAttribute("obj",obj);
        return "news/update";
    }
    /**
     * 执行修改
     */
    @RequestMapping("/exUpdate")
    public String exUpdate(News news){
        newsService.updateById(news);
        return "redirect:/news/findBySql";
    }
    /**
     * 删除
     */
    @RequestMapping("/delete")
    public String delete(Integer id){
        newsService.deleteById(id);
        return "redirect:/news/findBySql";
    }

    /**
     * 跳转到前端公告列表
     * @return
     */
    @RequestMapping("/list")
    public String list(Model model){
        //sql底层 如查询条件为null 则不条件查询，直接查询全表
        Pager<News> pagers = newsService.findByEntity(new News());
        model.addAttribute("pagers",pagers);
        return "news/list";
    }
    /**
     * 公告详情
     */
    @RequestMapping("/view")
    public String view(Model model,Integer id){
        News news=newsService.load(id);
        model.addAttribute("obj",news);
        return "news/view";
    }
}
