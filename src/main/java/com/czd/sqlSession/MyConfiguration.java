package com.czd.sqlSession;

import com.czd.config.Function;
import com.czd.config.MapperBean;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.print.Doc;
import javax.xml.parsers.SAXParser;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 读取与解析配置信息，并返回处理后的Environment
 *
 * @author: czd
 * @create: 2018/3/13 9:26
 */
public class MyConfiguration {
    private  static  ClassLoader loader=ClassLoader.getSystemClassLoader();

    /**
     * 读取xml并处理
     * @param resource
     * @return
     */
    public Connection build(String resource){
        try {
            InputStream stream = loader.getResourceAsStream(resource);
            SAXReader reader = new SAXReader();
            Document document = reader.read(stream);
            Element root = document.getRootElement();
            return evelDataSource(root);
        }catch (Exception e){
            throw new RuntimeException("error occured while evaling xml"+resource);
        }
    }

    /**
     * 具体解析xml并放回数据库Connection
     * @param node
     * @return
     * @throws ClassNotFoundException
     */
    private Connection evelDataSource(Element node) throws ClassNotFoundException {
        if(!"database".equals(node.getName())){
            throw new RuntimeException("root should be <database>");
        }
        String driverClassName=null;
        String url=null;
        String userName=null;
        String password=null;
        //获取属性节点
        for(Object item:node.elements("property")){
            Element i=(Element)item;
            String value=getValue(i);
            String name=i.attributeValue("name");
            if(name == null || value==null){
                throw new RuntimeException("[database]:<property>should contain name and value");
            }
            switch (name){
                case "url":
                    url=value;
                    break;
                case "userName":
                    userName=value;
                    break;
                case "password":
                    password=value;
                    break;
                case "driverClassName":
                    driverClassName=value;
                    break;
                default:
                    throw new RuntimeException("[database]:<property> unkown name");
            }
        }
        Class.forName(driverClassName);
        Connection conn=null;
        try {
            conn= DriverManager.getConnection(url,userName,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;

    }

    /***
     * 取Element元素的值
     * @param node
     * @return
     */
    private String getValue(Element node){
        return node.hasContent()?node.getText():node.attributeValue("value");
    }

    public MapperBean readMapper(String path){
        MapperBean mapper=new MapperBean();
        InputStream stream= loader.getResourceAsStream(path);
        SAXReader reader=new SAXReader();
        Document document= null;
        try {
            document = reader.read(stream);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element root =document.getRootElement();
        //把mapper节点的nameSpace值存为接口名
        mapper.setInterfaceName(root.attributeValue("nameSpace").trim());
        List<Function> list=new ArrayList<>();
        //遍历根节点下所有子节点
        for(Iterator rootIter=root.elementIterator();rootIter.hasNext();){
            Function fun=new Function();
            Element e= (Element) rootIter.next();
            String sqlType=e.getName().trim();
            String funcName=e.attributeValue("id").trim();
            String sql=e.getText().trim();
            String resultType=e.attributeValue("resultType").trim();
            fun.setSqlType(sqlType);
            fun.setFuncName(funcName);
            Object newInstance=null;
            try {
                newInstance =Class.forName(resultType);
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            fun.setResultType(newInstance);
            fun.setSql(sql);
            list.add(fun);
        }
        mapper.setList(list);
        return  mapper;
    }



}
