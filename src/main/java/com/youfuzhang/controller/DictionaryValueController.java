package com.youfuzhang.controller;
import com.youfuzhang.beans.DictionaryValue;
import com.youfuzhang.beans.DicyionaryType;
import com.youfuzhang.services.DictionaryValueServices;
import com.youfuzhang.services.DicyionaryTypeSerevices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/DictionaryValueController")
public class DictionaryValueController {

    @Autowired
    DictionaryValueServices dVs;
    @Autowired
    DicyionaryTypeSerevices dictionaryTypeServices;

    @RequestMapping("getAll.action")
    public Map getAll(){
        ArrayList<DictionaryValue> all = dVs.getAll();

        HashMap hashMap = new HashMap();
        hashMap.put("error",0);
        hashMap.put("data",all);
        return hashMap;
    }
    @RequestMapping("delAll.action")
    public Map delAll(String[] id){
        int i = dVs.delAll(id);
        HashMap hashMap = new HashMap();
        if (i < 0) {
            hashMap.put("error",1);
            hashMap.put("mess","删除失败");
        }
        else {
            hashMap.put("error",0);
            hashMap.put("mess","删除成功");
        }
        return hashMap;
    }

    @RequestMapping("add.action")
    public Map add(DictionaryValue dictionaryValue){
        UUID uuid = UUID.randomUUID();
        String replace = uuid.toString().replace("-", "");
       dictionaryValue.setId(replace);
        int add = dVs.add(dictionaryValue);
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
        DictionaryValue value = dVs.getId(id);
        ArrayList<DicyionaryType> all = dictionaryTypeServices.getAll();
        hashMap.put("DicyionaryValue",value);
        hashMap.put("type",all);
        return hashMap;
    }

    @RequestMapping("edit.action")
    public Map edit(DictionaryValue dictionaryValue){
        HashMap hashMap = new HashMap();
        int edit = dVs.edit(dictionaryValue);
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
}
