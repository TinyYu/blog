package com.ly.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ly.pojo.Blog;
import com.ly.pojo.Type;
import com.ly.service.BlogService;
import com.ly.service.TypeService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @RequestMapping("/types/{id}")
    public String types(@PathVariable Integer id, @RequestParam(defaultValue = "1") Integer pageNum, Model model){

        PageHelper.startPage(pageNum,10);
        List<Type> listType = typeService.getListType();
        List<Blog> blogByType = null;
        if (id == -1) {
            id = listType.get(0).getId();
            blogByType = blogService.getBlogByTypeId(id);
        } else {
            blogByType = blogService.getBlogByTypeId(id);
        }
        PageInfo<Blog> pageInfo = new PageInfo<>(blogByType);
        model.addAttribute("listBlog",pageInfo.getList());  //将封装好的数据返回到前台页面
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("listType",listType);
        model.addAttribute("activeTypeId",id);
        return "types";
    }
}
