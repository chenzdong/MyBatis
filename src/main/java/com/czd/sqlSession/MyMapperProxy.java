package com.czd.sqlSession;

import com.czd.config.Function;
import com.czd.config.MapperBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Mapper代理类
 *
 * @author: czd
 * @create: 2018/3/13 10:21
 */
public class MyMapperProxy implements InvocationHandler {
    private MySqlsession mySqlsession;
    private MyConfiguration myConfiguration;

    public MyMapperProxy(MyConfiguration myConfiguration,MySqlsession mySqlsession) {
        this.mySqlsession = mySqlsession;
        this.myConfiguration = myConfiguration;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MapperBean readMapper=myConfiguration.readMapper("UserMapper.xml");
        //是否为XML文件对应的接口
        if(!method.getDeclaringClass().getName().equals(readMapper.getInterfaceName())){
            return null;
        }
        List<Function> list=readMapper.getList();
        if(null !=list ||0!=list.size()){
            for(Function function:list){
                //id是否和接口方法一样
                if(method.getName().equals(function.getFuncName())){
                    return mySqlsession.selectOne(function.getSql(),String.valueOf(args[0]));
                }
            }
        }


        return null;
    }
}
