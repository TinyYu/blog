package com.ly.service;

import com.ly.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TagService {

    /**
     * 查询全部标签
     * @return
     */
    List<Tag> getListTag();

    /**
     * 根据name查询
     * @param name
     * @return
     */
    Tag getTagByName(String name);

    /**
     * 返回指定条数
     * @return
     */
    List<Tag> getListTagByCount(Integer count);

    /**
     * 根据id查询标签
     * @param id
     * @return
     */
    Tag getTagById(Integer id);

    /**
     * 添加标签
     * @param tag
     * @return
     */
    Integer addTog(Tag tag);

    /**
     * 修改标签
     * @param tag
     * @return
     */
    Integer updateTog(Tag tag);

    /**
     * 删除标签
     * @param id
     */
    void deleteTog(Integer id);
}
