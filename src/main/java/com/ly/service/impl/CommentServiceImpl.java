package com.ly.service.impl;

import com.ly.mapper.CommentMapper;
import com.ly.pojo.Comment;
import com.ly.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Override
    public Comment getCommentById(Integer id) {
        return commentMapper.getCommentById(id);
    }

    @Override
    public List<Comment> getCommentListByBlogId(Integer blogId) {
        return commentMapper.getCommentListByBlogId(blogId);
    }

    @Override
    public Integer addComment(Comment comment) {
        return commentMapper.addComment(comment);
    }

    @Override
    public List<Comment> getCommentByParentCommentId(Integer parentCommentId) {
        return commentMapper.getCommentByParentCommentId(parentCommentId);
    }
}
