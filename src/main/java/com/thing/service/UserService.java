package com.thing.service;

import com.thing.entity.User;

public interface UserService {
    User getById(int id);

    User getByName(String name);
}
