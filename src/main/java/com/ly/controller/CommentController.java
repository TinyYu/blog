package com.ly.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ly.pojo.Blog;
import com.ly.pojo.BlogUser;
import com.ly.pojo.Comment;
import com.ly.service.BlogService;
import com.ly.service.CommentService;
import com.ly.util.CommentUtils;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class CommentController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;

    @Value("${comment.avatar}")
    private String avatar;

    // 返回评论数据
    @RequestMapping("/comments/{blogId}")
    public String comments(@PathVariable("blogId") Integer blogId, Model model){
        Blog blog = blogService.getBlogById(blogId);
        List<Comment> commentListByBlogId = commentService.getCommentListByBlogId(blog.getBlogId());

        // 对评论进行处理
        getComment(commentListByBlogId);
        List<Comment> comments = eachComment(commentListByBlogId);

        blog.setComments(comments);

        model.addAttribute("blog",blog);
        return "blog :: commentList";
    }

    @RequestMapping("/comments")
    public String post(Comment comment, HttpSession session){
        if (comment.getCommentId() != -1){
            comment.setParentCommentId(comment.getCommentId());
        }

        comment.setCreateTime(new Date());
        BlogUser user = (BlogUser) session.getAttribute("user");
        if (user != null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
            comment.setNickname(user.getNickname());
        } else {
            comment.setAvatar(avatar);
            comment.setAdminComment(false);
        }


        Integer integer = commentService.addComment(comment);
        return "redirect:/comments/" + comment.getBlogId();
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
