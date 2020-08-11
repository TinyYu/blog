package com.ly.mapper;


import com.ly.pojo.BlogUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BlogUserMapper {
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
