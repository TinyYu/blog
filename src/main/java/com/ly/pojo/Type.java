package com.ly.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

// 分类
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Type {
    private Integer id; // 分类id
    private String name; // 分类名

    private List<Blog> blogs = new ArrayList<>(); // 博客
}
