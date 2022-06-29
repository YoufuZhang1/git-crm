package com.youfuzhang.services;

import com.youfuzhang.beans.DictionaryValue;
import com.youfuzhang.mapper.DictionaryValueMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author zyf
 * @date 2022年06月17日22:43时
 */
@Service
public class DictionaryValueServicesImpl implements DictionaryValueServices {
    @Autowired
    DictionaryValueMapper dvm;
    @Override
    public ArrayList<DictionaryValue> getAll() {
        return dvm.getAll();
    }

    @Override
    public DictionaryValue getId(String id) {
        return dvm.getId(id);
    }

    @Override
    public int add(DictionaryValue dictionaryValue) {
        return dvm.add(dictionaryValue);
    }

    @Override
    public int edit(DictionaryValue dictionaryValue) {
        return dvm.edit(dictionaryValue);
    }

    @Override
    public int del(DictionaryValue dictionaryValue) {
        return dvm.del(dictionaryValue);
    }

    @Override
    public int delAll(String[] id) {
        return dvm.delAll(id);
    }
}
