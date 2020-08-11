package com.ly.service.impl;

import com.ly.mapper.BlogMapper;
import com.ly.pojo.Blog;
import com.ly.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public List<Blog> getListBlog() {
        return blogMapper.getListBlog();
    }

    @Override
    public List<Blog> getListBlogNewest(Integer count) {
        return blogMapper.getListBlogNewest(count);
    }

    @Override
    public Blog getBlogById(Integer id) {
        return blogMapper.getBlogById(id);
    }

    @Override
    public List<Blog> getBlogByTitle(String title) {
        return blogMapper.getBlogByTitle(title);
    }

    @Override
    public List<Blog> getBlogByType(String type) {
        return blogMapper.getBlogByType(type);
    }

    @Override
    public List<Blog> getBlogByTypeId(Integer id) {
        return blogMapper.getBlogByTypeId(id);
    }

    @Override
    public List<Blog> getBlogByQBC(Map<String, Object> map) {
        return blogMapper.getBlogByQBC(map);
    }

    @Override
    public List<Blog> getBlogByTagId(Integer tagId) {
        return blogMapper.getBlogByTagId(tagId);
    }

    @Override
    public List<String> getBlogYear() {
        return blogMapper.getBlogYear();
    }

    @Override
    public List<Blog> getBlogByYear(String year) {
        return blogMapper.getBlogByYear(year);
    }

    @Override
    public Integer addBlog(Blog blog) {
        return blogMapper.addBlog(blog);
    }

    @Override
    public Integer updateBlog(Blog blog) {
        return blogMapper.updateBlog(blog);
    }

    @Override
    public void deleteBlog(Integer id) {
        blogMapper.deleteBlog(id);
    }

}
