package com.ly.service.impl;

import com.ly.mapper.TagMapper;
import com.ly.pojo.Tag;
import com.ly.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<Tag> getListTag() {
        return tagMapper.getListTag();
    }

    @Override
    public Tag getTagByName(String name) {
        return tagMapper.getTagByName(name);
    }

    @Override
    public List<Tag> getListTagByCount(Integer count) {
        return tagMapper.getListTagByCount(count);
    }

    @Override
    public Tag getTagById(Integer id) {
        return tagMapper.getTagById(id);
    }

    @Override
    public Integer addTog(Tag tag) {
        return tagMapper.addTog(tag);
    }

    @Override
    public Integer updateTog(Tag tag) {
        return tagMapper.updateTog(tag);
    }

    @Override
    public void deleteTog(Integer id) {
        tagMapper.deleteTog(id);
    }
}
