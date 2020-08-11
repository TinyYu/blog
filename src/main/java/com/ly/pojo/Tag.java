package com.ly.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

// 标签
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag {
    private Integer id; // 标签id
    private String name; // 标签名称

    private List<Blog> blogs = new ArrayList<>(); // 博客
}
