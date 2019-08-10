package com.niczo;

import com.niczo.dao.UserDao;
import com.niczo.dao.impl.UserDaoImpl;
import com.niczo.model.User;

import java.sql.SQLException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        UserDao userDao = new UserDaoImpl();
        try {
            User query = new User();
            query.setName("lhc");
            User user = userDao.getUser(query);
            System.out.println(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
