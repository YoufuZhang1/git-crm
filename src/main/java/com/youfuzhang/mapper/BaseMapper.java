package com.youfuzhang.mapper;

import java.util.ArrayList;

/**
 * @author zyf
 * @date 2022年06月16日19:46时
 */
public interface BaseMapper<T> {
    ArrayList<T> getAll();
    T getId(String id);
    int add(T t);
    int edit(T t);
    int del(T t);
    int delAll(String[] id);
}
