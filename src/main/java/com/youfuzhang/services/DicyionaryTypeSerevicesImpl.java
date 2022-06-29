package com.youfuzhang.services;

import com.youfuzhang.beans.DicyionaryType;
import com.youfuzhang.mapper.DicyionaryTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author zyf
 * @date 2022年06月16日19:48时
 */
@Service
public class DicyionaryTypeSerevicesImpl implements DicyionaryTypeSerevices {

    @Autowired
    DicyionaryTypeMapper dtm;
    @Override
    public ArrayList<DicyionaryType> getAll() {
        return dtm.getAll();
    }

    @Override
    public DicyionaryType getId(String id) {
        return dtm.getId(id);
    }

    @Override
    public int add(DicyionaryType dicyionaryType) {
        return dtm.add(dicyionaryType);
    }

    @Override
    public int edit(DicyionaryType dicyionaryType) {
        return dtm.edit(dicyionaryType);
    }

    @Override
    public int del(DicyionaryType dicyionaryType) {
        return dtm.del(dicyionaryType);
    }

    @Override
    public int delAll(String[] id) {
        return dtm.delAll(id);
    }
}
