package com.niczo.dao.impl;

import com.niczo.dao.UserDao;
import com.niczo.model.User;

import java.sql.*;

public class UserDaoImpl implements UserDao {
	@Override
	public User getUser(User param) throws SQLException {
		//Class.forName("com.mysql.jdbc.Driver");
		User result = null;

		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?characterEncoding=utf-8", "root", "123456")) {

			PreparedStatement preparedStatement = connection.prepareStatement("select * from user where name = ?");

			preparedStatement.setString(1, param.getName());

			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				result = new User();
				result.setId(resultSet.getInt(1));
				result.setName(resultSet.getString(2));
				result.setAge(resultSet.getInt(3));
				result.setWeight(resultSet.getDouble(4));
			}
		}
		return result;
	}
}
