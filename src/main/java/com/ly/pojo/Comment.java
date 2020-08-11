package com.ly.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// 评论
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private Integer commentId; // 评论id
    private String nickname; // 评论人的昵称
    private String email; //评论人的邮箱
    private String content; // 内容
    private String avatar; // 头像
    private Date createTime; //时间
    private Integer blogId;  // 博客id
    private Integer parentCommentId; // 对应的父id
    private boolean adminComment; // 是否是管理

    private Blog blog; // 博客

    private List<Comment> replyComments = new ArrayList<>(); //评论回复

    private Comment parentComment; //父评论

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    @Override
    public String toString() {
        return "";
    }
}
