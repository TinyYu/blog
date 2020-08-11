package com.ly.mapper;

import com.ly.pojo.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface BlogMapper {

    /**
     * 查询所有博客
     */
    List<Blog> getListBlog();

    /**
     * 根据id查询博客
     * @param id
     * @return
     */
    Blog getBlogById(Integer id);

    /**
     * 最新推荐
     * @param count
     * @return
     */
    List<Blog> getListBlogNewest(Integer count);

    /**
     * 根据博客标题查询
     * @param title
     * @return
     */
    List<Blog> getBlogByTitle(String title);


    /**
     * 根据博客类型查询
     * @param type
     * @return
     */
    List<Blog> getBlogByType(String type);
    List<Blog> getBlogByTypeId(Integer id);

    /**
     * 条件查询
     * @param map
     * @return
     */
    List<Blog> getBlogByQBC(Map<String,Object> map);

    /**
     * 标签id查询
     * @param tagId
     * @return
     */
    List<Blog> getBlogByTagId(Integer tagId);

    /**
     * 根据年份返回博客列表
     * @return
     */
    List<String> getBlogYear();

    /**
     * 根据年份返回博客列表
     * @param year
     * @return
     */
    List<Blog> getBlogByYear(String year);

    /**
     * 添加博客
     * @param blog
     * @return
     */
    Integer addBlog(Blog blog);

    /**
     * 修改博客
     * @param blog
     * @return
     */
    Integer updateBlog(Blog blog);

    /**
     * 删除博客
     * @param id
     */
    void deleteBlog(Integer id);



}
