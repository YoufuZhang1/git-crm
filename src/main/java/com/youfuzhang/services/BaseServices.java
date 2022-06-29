package com.youfuzhang.services;

import java.util.ArrayList;

public interface BaseServices<T> {
    ArrayList<T> getAll();
        T getId(String id);
        int add(T t);
        int edit(T t);
        int del(T t);
        int delAll(String[] id);
}
