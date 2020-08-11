package com.ly.util;

import com.ly.pojo.Comment;
import com.ly.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

// 处理回复到前端的评论格式
public class CommentUtils {

    @Autowired
    private CommentService commentService;

    /**
     * 处理将数据库中的子评论封装到顶级评论（也就是第一级评论）
     * @param commentList
     */
    public void getComment(List<Comment> commentList){
        if (commentList != null) {
            for (Comment comment : commentList) {
                System.out.println(comment.getContent());
                List<Comment> commentByParentCommentId = commentService.getCommentByParentCommentId(comment.getCommentId());
                if (commentByParentCommentId != null){
                    for (Comment comment1 : commentByParentCommentId) {
                        comment1.setParentComment(comment);
                    }
                    comment.setReplyComments(commentByParentCommentId);
                    getComment(comment.getReplyComments());
                }
            }
        }
    }


    // 处理评论
    /**
     * 复制查询到的数据集合 -- 顶级评论
     *    处理复制出来的数据集合
     */
    public List<Comment> eachComment(List<Comment> comments){
        // 复制一个原来的集合comments
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);  // 复制
            commentsView.add(c);
        }
        combineChildren(commentsView);
        return commentsView;
    }

    // 处理数据集合
    private void combineChildren(List<Comment> comments){
        for (Comment comment : comments) {  // -- 顶级评论
            List<Comment> replys = comment.getReplyComments();  // -- 子评论
            for (Comment reply : replys) {
                // 循环迭代，找出子代，存放在tempReplys集合中
                recursively(reply);
            }
            comment.setReplyComments(tempReplys);
            tempReplys = new ArrayList<>();
        }
    }

    private List<Comment> tempReplys = new ArrayList<>();  // 外部集合，存放所有的子评论，该外部集合作为第二级评论

    private void recursively(Comment comment){
        tempReplys.add(comment); // 将子评论，存放到外部集合

        /**
         * 递归方式
         */
        if (comment.getReplyComments().size() > 0){ // 如果不为0，表示子评论下还有子评论
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys){
                tempReplys.add(reply); // 将子评论，存放到外部集合
                if (reply.getReplyComments().size() > 0){
                    recursively(reply);
                }
            }
        }
    }

}
