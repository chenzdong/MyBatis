package com.czd.sqlSession;

import com.czd.bean.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author: czd
 * @create: 2018/3/13 10:11
 */
public class MyExcutor implements  Excutor{
    private MyConfiguration xmlConfiguration=new MyConfiguration();
    //具体查询
    @Override
    public <T> T query(String sql, Object parameter) {
        Connection connection=getConnection();
        ResultSet set=null;
        PreparedStatement pre=null;
        try {
            pre=connection.prepareStatement(sql);
            pre.setString(1,parameter.toString());
            set =pre.executeQuery();
            User u=new User();
            while(set.next()){
                u.setId(set.getString(1));
                u.setUsernames(set.getString(2));
                u.setPassword(set.getString(3));
            }
            return (T) u;

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
            if(set!=null){
                set.close();
            }
            if(pre!=null){
                pre.close();
            }
            if(connection!=null) {
                connection.close();
            }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return  null;
    }

    private  Connection getConnection(){
        Connection connection=xmlConfiguration.build("config.xml");
        return  connection;
    }
}
