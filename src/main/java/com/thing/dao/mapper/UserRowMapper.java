package com.thing.dao.mapper;

import com.thing.entity.User;
import com.thing.entity.UserType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper {
    public User mapRow(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setUserName(resultSet.getString("userName"));
        user.setPassword(resultSet.getString("password"));
        UserType userType = UserType.getByName(resultSet.getString("userType"));
        user.setUserType(userType);

        return user;
    }
}
