package com.ly.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// 博客
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog {
    private Integer blogId;  // 博客id
    private String title; // 博客标题
    private String content; // 内容
    private String firstPicture; // 首图
    private String flag; // 标记
    private Integer views; // 浏览次数
    private boolean appreciation; // 赞赏事务 开/关
    private boolean shareStatement; // 转载声明 开/关
    private boolean comment; // 评论 开/关
    private boolean published; // 发布/保存
    private boolean recommend; // 推荐 是/否
    private Date createTime; // 发布时间
    private Date updateTime; // 更新时间
    private Integer typeId; // 分类id
    private Integer userId; // 用户id
    private String blogdescribe; // 博客描述

    private Type types; // 分类

    private List<Tag> tags = new ArrayList<>(); // 标签

    private BlogUser user; // 用户

    private List<Comment> comments = new ArrayList<>(); // 评论

    public String getCreateTime() {
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );
        if (createTime == null){
            return "";
        } else {
            String str = sdf.format(createTime);
            return str;
        }
    }

    public String getUpdateTime() {
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );
        if (updateTime == null){
            return "";
        } else {
            String str = sdf.format(updateTime);
            return str;
        }
    }
}
