package com.ly.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ly.pojo.Blog;
import com.ly.pojo.Comment;
import com.ly.pojo.Tag;
import com.ly.pojo.Type;
import com.ly.service.BlogService;
import com.ly.service.CommentService;
import com.ly.service.TagService;
import com.ly.service.TypeService;
import com.ly.util.MarkdownUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentService commentService;

    @RequestMapping({"/","/index.html","index"})
    public String index(@RequestParam(defaultValue = "1") Integer pageNum, Model model){
        PageHelper.startPage(pageNum,10);       // pageNum:当前页码数，第一次进来时默认为1（首页）
        List<Blog> listBlog = blogService.getListBlog(); //list:页面要展示的数据的集合
        PageInfo<Blog> pageInfo = new PageInfo<>(listBlog); //pageInfo:将分页数据和显示的数据封装到PageInfo当中
        List<Type> listTypeByCount = typeService.getListTypeByCount(6);  // 指定渲染6个分类
        List<Tag> listTagByCount = tagService.getListTagByCount(10);   // 指定渲染10个标签
        List<Blog> listBlogNewest = blogService.getListBlogNewest(4); // 指定渲染4个最新推荐
        List<Type> typeByBlogId = typeService.getTypeByBlogId();  // 每个分类有多少个博客
        model.addAttribute("listBlog",pageInfo.getList());  //将封装好的数据返回到前台页面
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("listType",listTypeByCount);
        model.addAttribute("listTag",listTagByCount);
        model.addAttribute("listBlogNewest",listBlogNewest);
        model.addAttribute("typeByBlogId",typeByBlogId);
        return "index";
    }

    @RequestMapping("/blog/{id}")
    public String blog(@PathVariable("id") Integer id,Model model){
        Blog blog = blogService.getBlogById(id);

        blog.setViews(blog.getViews() + 1);
        blogService.updateBlog(blog);

        // 处理博客文章的内容
        String content = blog.getContent();
        String s = MarkdownUtils.markdownToHtmlExtensions(content);
        blog.setContent(s);

        // 评论
        List<Comment> commentList = commentService.getCommentListByBlogId(blog.getBlogId());
        // 对评论进行处理
        getComment(commentList);
        List<Comment> comments = eachComment(commentList);
        blog.setComments(commentList);

        model.addAttribute("blog",blog);
        return "blog";
    }

    @RequestMapping("/search")
    public String search(@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam("query") String query,Model model){
        PageHelper.startPage(pageNum,10);
        Map<String,Object> map = new HashMap<>();
        String title = "%" + query + "%";
        String content = "%" + query + "%";
        map.put("title",title);
        map.put("content",content);
        List<Blog> blogByQBC = blogService.getBlogByQBC(map);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogByQBC);
        model.addAttribute("listBlog",pageInfo.getList());
        model.addAttribute("pageInfo",pageInfo);
        return "search";
    }


    public void getComment(List<Comment> commentList){
        if (commentList != null) {
            for (Comment comment : commentList) {
                List<Comment> commentByParentCommentId = commentService.getCommentByParentCommentId(comment.getCommentId());
                for (Comment comment1 : commentByParentCommentId) {
                    comment1.setParentComment(comment);
                }
                comment.setReplyComments(commentByParentCommentId);
                getComment(comment.getReplyComments());
            }
        }
    }

    // 处理评论
    /**
     *
     */
    private List<Comment> eachComment(List<Comment> comments){
        // 复制一个原来的集合comments
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        combineChildren(commentsView);
        return commentsView;
    }

    private void combineChildren(List<Comment> comments){
        for (Comment comment : comments) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                // 循环迭代，找出子代，存放在tempReplys集合中
                recursively(reply);
            }
            comment.setReplyComments(tempReplys);
            tempReplys = new ArrayList<>();
        }
    }

    private List<Comment> tempReplys = new ArrayList<>();

    private void recursively(Comment comment){
        tempReplys.add(comment); // 顶节点添加到临时存放集合
        if (comment.getReplyComments().size() > 0){
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys){
                tempReplys.add(reply);
                if (reply.getReplyComments().size() > 0){
                    recursively(reply);
                }
            }
        }
    }

}
