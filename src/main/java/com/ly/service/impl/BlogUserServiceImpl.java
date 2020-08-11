package com.ly.service.impl;

import com.ly.mapper.BlogUserMapper;
import com.ly.pojo.BlogUser;
import com.ly.service.BlogUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogUserServiceImpl implements BlogUserService {

    @Autowired
    private BlogUserMapper blogUserMapper;

    @Override
    public BlogUser getBlogUserById(Integer id) {
        return blogUserMapper.getBlogUserById(id);
    }

    @Override
    public BlogUser getBlogUserByName(String name) {
        return blogUserMapper.getBlogUserByName(name);
    }
}
