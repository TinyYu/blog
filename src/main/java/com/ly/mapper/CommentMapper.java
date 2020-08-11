package com.ly.mapper;

import com.ly.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {

    /**
     * 根据id查询评论
     * @param id
     * @return
     */
    Comment getCommentById(Integer id);

    /**
     * 获取博客下所有的评论
     * @return
     */
    List<Comment> getCommentListByBlogId(Integer blogId);


    /**
     * 添加方法
     * @param comment
     * @return
     */
    Integer addComment(Comment comment);

    /**
     * parentCommentId查询评论
     * @param parentCommentId
     * @return
     */
    List<Comment> getCommentByParentCommentId(Integer parentCommentId);

}
