package com.youfuzhang.controller;

import com.youfuzhang.beans.DicyionaryType;
import com.youfuzhang.services.DicyionaryTypeSerevices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zyf
 * @date 2022年06月16日20:18时
 */
@RestController
@RequestMapping("/DicyionaryTypeController")
public class DicyionaryTypeController {

    @Autowired
    DicyionaryTypeSerevices dts;

    @RequestMapping("getAll.action")
    public Map getAll(){
        ArrayList<DicyionaryType> all = dts.getAll();

        HashMap hashMap = new HashMap();
        hashMap.put("error",0);
        hashMap.put("data",all);
        return hashMap;
    }

    @RequestMapping("add.action")
    public Map add(DicyionaryType dicyionaryType){
        LocalDateTime now = LocalDateTime.now();
        String format = now.format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss:SSS"));
        dicyionaryType.setCreatTime(format);
        int add = dts.add(dicyionaryType);
        HashMap hashMap = new HashMap();
        if (add < 1) {
            hashMap.put("error",1);
            hashMap.put("mess","学生添加失败");
        }
        else {
            hashMap.put("error",0);
            hashMap.put("mess","学生添加成功");
        }
        return hashMap;
    }

    @RequestMapping("editBefore.action")
    public Map editBefore(String id){
        HashMap hashMap = new HashMap();
        DicyionaryType type = dts.getId(id);
        hashMap.put("DicyionaryType",type);
        return hashMap;
    }

    @RequestMapping("edit.action")
    public Map edit(DicyionaryType dicyionaryType){
        HashMap hashMap = new HashMap();
        int edit = dts.edit(dicyionaryType);
        if (edit < 1) {
            hashMap.put("error",1);
            hashMap.put("mess","学生修改失败");
        }
        else {
            hashMap.put("error",0);
            hashMap.put("mess","学生修改成功");
        }
        return hashMap;
    }
    @RequestMapping("delAll.action")
    public Map delAll(String[] id){
        int i = dts.delAll(id);
        HashMap hashMap = new HashMap();
        if (i < 0) {
            hashMap.put("error",1);
            hashMap.put("mess","学生删除失败");
        }
        else {
            hashMap.put("error",0);
            hashMap.put("mess","学生删除成功");
        }
        return hashMap;
    }


}
