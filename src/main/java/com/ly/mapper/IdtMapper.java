package com.ly.mapper;

import com.ly.pojo.Idt;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface IdtMapper {

    /**
     * 查询全部
     * @return
     */
    List<Idt> getIdt();

    /**
     * 根据blogId查询
     * @param blogId
     * @return
     */
    List<Idt> getIdtByBlogId(Integer blogId);

    /**
     * 根据tagId查询
     * @param tagId
     * @return
     */
    List<Idt> getIdtByTagId(Integer tagId);

    /**
     * 添加数据
     * @param idt
     * @return
     */
    Integer addIdt(Idt idt);


    /**
     * 根据博客id 删除博客对应的标签
     * @param blogId
     */
    void deleteIdt(Integer blogId);
}
