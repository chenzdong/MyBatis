package com.czd.mapper;

import com.czd.bean.User;

/**
 * user映射类
 *
 * @author: czd
 * @create: 2018/3/12 16:56
 */
public interface UserMapper {
    public User getUserById(String id);
}
