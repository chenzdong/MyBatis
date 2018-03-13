package com.czd.sqlSession;

import java.lang.reflect.Proxy;

/**
 * @author: czd
 * @create: 2018/3/13 10:09
 */
public class MySqlsession {
    private Excutor excutor=new MyExcutor();
    private MyConfiguration myConfiguration=new MyConfiguration();
    public<T> T selectOne(String statement,Object parameter){
        return excutor.query(statement,parameter);
    }
    public<T> T getMapper(Class clazz){
        return (T)Proxy.newProxyInstance(clazz.getClassLoader(),new Class[]{clazz},new MyMapperProxy(myConfiguration,this));
    }

}
