package com.czd.sqlSession;

/**
 * @author: czd
 * @create: 2018/3/13 10:10
 */
public interface Excutor {
    public <T> T query(String statement,Object parameter);
}
