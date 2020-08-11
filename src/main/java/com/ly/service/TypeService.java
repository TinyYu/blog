package com.ly.service;

import com.ly.pojo.Type;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

public interface TypeService {

    /**
     * 查询全部
     * @return
     */
    List<Type> getListType();

    /**
     * 返回指定条数
     * @return
     */
    List<Type> getListTypeByCount(Integer count);

    /**
     * 根据id查询类型
     * @param id
     * @return
     */
    Type getTypeById(Integer id);

    /**
     * 根据博客id分组查询
     * @return
     */
    List<Type> getTypeByBlogId();

    /**
     * 根据name查询
     * @param name
     * @return
     */
    Type getTypeByName(String name);

    /**
     * 添加分类
     * @param type
     */
    Integer addType(Type type);

    /**
     * 修改分类
     * @param type
     */
    Integer updateType(Type type);

    /**
     * 删除
     * @param id
     */
    void deleteType(Integer id);
}
