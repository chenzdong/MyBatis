package com.czd;

import com.czd.bean.User;
import com.czd.mapper.UserMapper;
import com.czd.sqlSession.MyMapperProxy;
import com.czd.sqlSession.MySqlsession;

/**
 * 测试Mybatis
 *
 * @author: czd
 * @create: 2018/3/13 10:33
 */
public class TestMybatis {
    public static void main(String[] args) {
        MySqlsession sqlsession=new MySqlsession();
        UserMapper mapper=sqlsession.getMapper(UserMapper.class);
        User user=mapper.getUserById("0");
        System.out.println(user);
    }

}
