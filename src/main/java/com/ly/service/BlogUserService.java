package com.ly.service;

import com.ly.pojo.BlogUser;

public interface BlogUserService {

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    BlogUser getBlogUserById(Integer id);

    /**
     * 根据用户名查询用户
     * @param name
     * @return
     */
    BlogUser getBlogUserByName(String name);
}
