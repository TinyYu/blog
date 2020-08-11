package com.ly.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ly.pojo.Blog;
import com.ly.pojo.Tag;
import com.ly.pojo.Type;
import com.ly.service.BlogService;
import com.ly.service.TagService;
import com.ly.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    @RequestMapping("/tags/{id}")
    public String types(@PathVariable Integer id, @RequestParam(defaultValue = "1") Integer pageNum, Model model){
        PageHelper.startPage(pageNum,10);
        List<Tag> listTag = tagService.getListTag();
        List<Blog> blogByTag = null;
        if (id == -1) {
            id = listTag.get(0).getId();
            blogByTag = blogService.getBlogByTagId(id);
        } else {
            blogByTag = blogService.getBlogByTagId(id);
        }
        PageInfo<Blog> pageInfo = new PageInfo<>(blogByTag);
        model.addAttribute("listBlog",pageInfo.getList());  //将封装好的数据返回到前台页面
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("listTag",listTag);
        model.addAttribute("activeTagId",id);
        return "tags";
    }
}
