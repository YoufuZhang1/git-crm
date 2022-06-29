package com.youfuzhang.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zyf
 * @date 2022年06月17日22:38时
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DictionaryValue {
    private String id;
    private String value;
    private String text;
    private String orderNo;
    private String typeid;
    private DicyionaryType dicyionaryType;
}
