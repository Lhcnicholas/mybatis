package com.niczo;

import static org.junit.Assert.assertTrue;

import com.niczo.dao.UserDao;
import com.niczo.dao.impl.UserDaoImpl;
import com.niczo.model.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    private static final Logger logger = LoggerFactory.getLogger(AppTest.class);
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void test1() throws SQLException {
        UserDao userDao = new UserDaoImpl();
        User query = new User();
        query.setName("lhc");
        User user = userDao.getUser(query);
        logger.info(user.toString());
    }
}
