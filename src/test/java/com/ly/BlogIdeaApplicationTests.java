package com.ly;

import com.ly.pojo.Blog;
import com.ly.pojo.Comment;
import com.ly.pojo.Tag;
import com.ly.pojo.Type;
import com.ly.service.BlogService;
import com.ly.service.CommentService;
import com.ly.service.TagService;
import com.ly.service.TypeService;
import com.ly.util.CommentUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class BlogIdeaApplicationTests {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private TagService tagService;

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

    @Test
    void cd(){
        Map<String,List<Blog>> map = new HashMap<>();
        List<String> blogYear = blogService.getBlogYear();
        for (String s : blogYear) {
            map.put(s,blogService.getBlogByYear(s));
        }
        System.out.println(map.size());
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

//    @Test
//    void cd1(){
//
//        Blog blog = new Blog();
//        List<Comment> commentListByBlogId = commentService.getCommentListByBlogId(1);
//
//        // 对评论进行处理
//        CommentUtils commentUtils = new CommentUtils();
//        commentUtils.getComment(commentListByBlogId);
//        List<Comment> comments = commentUtils.eachComment(commentListByBlogId);
//        for (Comment comment : comments) {
//            System.out.println(comment.getContent());
//            for (Comment replyComment : comment.getReplyComments()) {
//                System.out.println(replyComment.getContent());
//            }
//        }
//        blog.setComments(commentListByBlogId);
//    }
}
