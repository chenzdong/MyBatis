package com.czd.config;

import lombok.Data;

/**
 * Sql相关基类
 *
 * @author: czd
 * @create: 2018/3/13 9:51
 */
@Data
public class Function {
    private String sqlType;
    private String funcName;
    private String sql;
    private Object resultType;
    private String parameterType;
}
