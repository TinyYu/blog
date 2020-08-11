package com.ly.controller;

import com.ly.pojo.Blog;
import com.ly.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ArchiveShowController {

    @Autowired
    private BlogService blogService;

    @RequestMapping("/archives")
    public String archives(Model model){

        Map<String, List<Blog>> map = new HashMap<>();
        List<String> blogYear = blogService.getBlogYear();
        for (String s : blogYear) {
            map.put(s,blogService.getBlogByYear(s));
        }
        model.addAttribute("blogSize",blogService.getListBlog().size());
        model.addAttribute("archiveMap",map);
        return "archives";
    }
}
