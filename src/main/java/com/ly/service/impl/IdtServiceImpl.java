package com.ly.service.impl;

import com.ly.mapper.IdtMapper;
import com.ly.pojo.Idt;
import com.ly.service.IdtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IdtServiceImpl implements IdtService {

    @Autowired
    private IdtMapper idtMapper;

    @Override
    public List<Idt> getIdt() {
        return idtMapper.getIdt();
    }

    @Override
    public List<Idt> getIdtByBlogId(Integer blogId) {
        return idtMapper.getIdtByBlogId(blogId);
    }

    @Override
    public List<Idt> getIdtByTagId(Integer tagId) {
        return idtMapper.getIdtByTagId(tagId);
    }

    @Override
    public Integer addIdt(Idt idt) {
        return idtMapper.addIdt(idt);
    }

    @Override
    public void deleteIdt(Integer blogId) {
        idtMapper.deleteIdt(blogId);
    }
}
