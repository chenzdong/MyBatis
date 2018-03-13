package com.czd.config;

import lombok.Data;

import java.util.List;

/**
 * Mapper原型类
 *
 * @author: czd
 * @create: 2018/3/13 9:50
 */
@Data
public class MapperBean {
    //mapper对应的接口
    private String interfaceName;
    //mapper配置文件中的sql具体字段
    private List<Function> list;

}
