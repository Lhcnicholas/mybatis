package com.niczo.dao;

import com.niczo.model.User;

import java.sql.SQLException;

public interface UserDao {

	User getUser(User param) throws SQLException;
}
