package com.ly.service.impl;

import com.ly.mapper.TypeMapper;
import com.ly.pojo.Type;
import com.ly.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    @Override
    public List<Type> getListType() {
        return typeMapper.getListType();
    }

    @Override
    public List<Type> getListTypeByCount(Integer count) {
        return typeMapper.getListTypeByCount(count);
    }


    @Override
    public Type getTypeById(Integer id) {
        return typeMapper.getTypeById(id);
    }

    @Override
    public List<Type> getTypeByBlogId() {
        return typeMapper.getTypeByBlogId();
    }

    @Override
    public Type getTypeByName(String name) {
        return typeMapper.getTypeByName(name);
    }

    @Override
    public Integer addType(Type type) {
        return typeMapper.addType(type);
    }

    @Override
    public Integer updateType(Type type) {
        return typeMapper.updateType(type);
    }

    @Override
    public void deleteType(Integer id) {
        typeMapper.deleteType(id);
    }
}
