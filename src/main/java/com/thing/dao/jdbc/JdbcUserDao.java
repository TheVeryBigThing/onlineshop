package com.thing.dao.jdbc;

import com.thing.dao.UserDao;
import com.thing.dao.mapper.UserRowMapper;
import com.thing.entity.User;

import java.sql.*;

public class JdbcUserDao implements UserDao {
    private static final String URI = "jdbc:sqlite:src\\main\\resources\\shop.db";

    @Override
    public User getById(int id) {

        try (Connection connection = DriverManager.getConnection(URI);
             Statement statement = connection.createStatement()) {

            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE id=" + id + ";")) {
                UserRowMapper userRowMapper = new UserRowMapper();
                resultSet.next();
                User user = userRowMapper.mapRow(resultSet);
                if (resultSet.next()) {
                    throw new RuntimeException("More than one user found by id: " + id);
                }
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Get user failed", e);
        }
    }

    @Override
    public User getByName(String name) {
        try (Connection connection = DriverManager.getConnection(URI);
             Statement statement = connection.createStatement()) {

            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE userName='" + name + "';")) {
                UserRowMapper userRowMapper = new UserRowMapper();
                resultSet.next();
                User user = userRowMapper.mapRow(resultSet);
                if (resultSet.next()) {
                    throw new RuntimeException("More than one user found by name: " + name);
                }

                return user;
            }


        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Get user failed", e);
        }
    }
}
