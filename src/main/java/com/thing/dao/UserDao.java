package com.thing.dao;

import com.thing.entity.User;

public interface UserDao {

    User getById(int id);

    User getByName(String name);
}
