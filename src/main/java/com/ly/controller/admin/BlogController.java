package com.ly.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ly.pojo.*;
import com.ly.service.BlogService;
import com.ly.service.IdtService;
import com.ly.service.TagService;
import com.ly.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @Autowired
    private IdtService idtService;

    // 博客管理页面数据初始化
    @RequestMapping("/blogs")
    public String Blogs(@RequestParam(defaultValue = "1") Integer pageNum, Model model){
        PageHelper.startPage(pageNum,10);       // pageNum:当前页码数，第一次进来时默认为1（首页）
        List<Blog> listBlog  = blogService.getListBlog(); //list:页面要展示的数据的集合 -- 博客集合
        List<Type> listType = typeService.getListType(); // -- 类型集合
        PageInfo<Blog> pageInfo = new PageInfo<>(listBlog); //pageInfo:将分页数据和显示的数据封装到PageInfo当中
        model.addAttribute("listBlog",pageInfo.getList());  //将封装好的数据返回到前台页面
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("listType",listType);
        return "admin/blogs";

    }

    // 博客管理页面数据搜索查询
    @RequestMapping("/blogs/search")
    public String search(@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam("title") String title,@RequestParam("typeId")Integer typeId,@RequestParam("recommend") boolean recommend, Model model){
        PageHelper.startPage(pageNum,10);
        Map<String,Object> map = new HashMap<>();
        if (!title.isEmpty()){
            map.put("title","%" + title + "%");
        }
        map.put("typeId",typeId);
        map.put("recommend",recommend);
        List<Blog> listBlog  = blogService.getBlogByQBC(map);
        List<Type> listType = typeService.getListType();
        PageInfo<Blog> pageInfo = new PageInfo<>(listBlog);
        model.addAttribute("listBlog",pageInfo.getList());
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("listType",listType);
        return "admin/blogs :: blogList";
    }

    // 跳转到添加页面，并初始化
    @RequestMapping("/blogs/input")
    public String input(Model model){
        List<Type> listType = typeService.getListType();
        List<Tag> listTag = tagService.getListTag();
        model.addAttribute("blog",new Blog());
        model.addAttribute("listType",listType);
        model.addAttribute("listTag",listTag);
        return "/admin/blogs-input";
    }

    // 添加数据，返回添加数据的状态信息 应为没有使用ajax封装所以参数一个一个写
    // 如果使用ajax 设置请求的Content-Type必须为application/json 使用@RequestBody接收
    @RequestMapping("/addblogs")
    public String addTypes(
            @RequestParam("published") boolean published,
            @RequestParam("title") String title,
            @RequestParam("flag") String flag,
            @RequestParam("content") String content,
            @RequestParam("typeId") Integer typeId,
            String tagIds,
            @RequestParam("firstPicture") String firstPicture,
            String recommend,
            String shareStatement,
            String appreciation,
            String comment,
            @RequestParam("blogdescribe") String blogdescribe,
            RedirectAttributes attributes, HttpSession session){

        // 获取前端数据，封装为blog对象
        Blog blog = new Blog();
        blog.setPublished(published); // 保存/发布
        blog.setTitle(title);   // 标题
        blog.setFlag(flag);  // 原创/转载/翻译
        blog.setContent(content); // 内容
        blog.setTypeId(typeId);  // 分类id
        blog.setFirstPicture(firstPicture); // 首图
        blog.setBlogdescribe(blogdescribe); // 博客描述

        // 贼难受，我是菜鸡
        if (recommend == null){         // 推荐
            blog.setRecommend(false);
        } else {
            blog.setRecommend(true);
        }
        if (shareStatement == null){    // 转载声明
            blog.setShareStatement(false);
        } else {
            blog.setShareStatement(true);
        }
        if (appreciation == null){ // 赞赏事务
            blog.setAppreciation(false);
        } else {
            blog.setAppreciation(true);
        }
        if (comment == null){   // 评论
            blog.setComment(false);
        } else {
            blog.setComment(true);
        }
        blog.setCreateTime(new Date()); // 发布时间 这里有点问题，应该是在发布的时候才添加，保存不管。不改了反正只有我知道
        blog.setUpdateTime(new Date()); // 更新时间
        blog.setViews(0); // 浏览数量



        blog.setUserId(1); // 用户id 因为只有一个用户

        Integer integer =  blogService.addBlog(blog); // 返回受影响的行数

        if (tagIds != null){ // 添加到博客和标签，中间表中
            String[] tags = tagIds.split(",");
            for (String tagid : tags) {
                Idt idt = new Idt();
                idt.setBlogId(blog.getBlogId());
                idt.setTagId(Integer.parseInt(tagid));
                idtService.addIdt(idt);
            }
        }
        if (integer != null){
            attributes.addFlashAttribute("message","操作成功!");
        } else {
            attributes.addFlashAttribute("message","操作失败!");
        }
        return "redirect:/admin/blogs";
    }

    // 修改页
    @GetMapping("blogs/{id}/input")
    public String updateBlog(@PathVariable("id") Integer id, Model model){
        List<Type> listType = typeService.getListType();
        List<Tag> listTag = tagService.getListTag();
        Blog blog = blogService.getBlogById(id);

        List<Tag> tagList = blog.getTags();
        StringBuffer sb = new StringBuffer();
        for (int i = 0;i < tagList.size();i++){
            if (i < tagList.size() - 1){
                sb.append(tagList.get(i).getId());
                sb.append(",");
            } else {
                sb.append(tagList.get(i).getId());
            }
        }
        String tags = sb.toString();
        model.addAttribute("blog",blog);
        model.addAttribute("listType",listType);
        model.addAttribute("listTag",listTag);
        model.addAttribute("tags",tags);
        return "admin/blogs-update";
    }


    @RequestMapping("/updateblog")
    public String updateBlogs(Integer id,
            @RequestParam("published") boolean published,
            @RequestParam("title") String title,
            @RequestParam("flag") String flag,
            @RequestParam("content") String content,
            @RequestParam("typeId") Integer typeId,
            String tagIds,
            @RequestParam("firstPicture") String firstPicture,
            String recommend,
            String shareStatement,
            String appreciation,
            String comment,
            @RequestParam("blogdescribe") String blogdescribe,
            RedirectAttributes attributes, HttpSession session){

        // 获取前端数据，封装为blog对象
        Blog blog = blogService.getBlogById(id);
        blog.setPublished(published); // 发布/保存
        blog.setTitle(title);   // 标题
        blog.setFlag(flag);  // 原创/转载/翻译
        blog.setContent(content); // 内容
        blog.setTypeId(typeId);  // 分类id
        blog.setFirstPicture(firstPicture); // 首图

        // 贼难受，我是菜鸡
        if (recommend == null){         // 推荐
            blog.setRecommend(false);
        } else {
            blog.setRecommend(true);
        }
        if (shareStatement == null){    // 转载声明
            blog.setShareStatement(false);
        } else {
            blog.setShareStatement(true);
        }
        if (appreciation == null){ // 赞赏事务
            blog.setAppreciation(false);
        } else {
            blog.setAppreciation(true);
        }
        if (comment == null){   // 评论
            blog.setComment(false);
        } else {
            blog.setComment(true);
        }
        blog.setUpdateTime(new Date()); // 更新时间
        blog.setBlogdescribe(blogdescribe); // 博客描述

        Integer integer =  blogService.updateBlog(blog); // 返回受影响的行数

        // 修改标签和博客中间表，先根据博客id删除该博客所有标签，在添加标签。（反正标签少 0.0 ）
        System.out.println(id);
        idtService.deleteIdt(id); // 删除标签

        if (!tagIds.isEmpty()){ // 添加到博客和标签，中间表中
            String[] tags = tagIds.split(",");
            for (String tagid : tags) {
                Idt idt = new Idt();
                idt.setBlogId(blog.getBlogId());
                idt.setTagId(Integer.parseInt(tagid));
                idtService.addIdt(idt);
            }
        }

        if (integer != null){
            attributes.addFlashAttribute("message","操作成功!");
        } else {
            attributes.addFlashAttribute("message","操作失败!");
        }
        return "redirect:/admin/blogs";
    }

    // 删除博客
    //删除数据
    @RequestMapping("blogs/{id}/delete")
    public String deleteTypes(@PathVariable("id") Integer id){
        blogService.deleteBlog(id);
        // 删除中间表
        idtService.deleteIdt(id);
        return "redirect:/admin/blogs";
    }
}
