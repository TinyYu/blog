package com.ly.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 博客和标签的中间表
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Idt {
    private Integer blogId;
    private Integer tagId;
}
