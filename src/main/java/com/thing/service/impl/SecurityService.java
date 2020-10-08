package com.thing.service.impl;

import com.thing.dao.UserDao;
import com.thing.entity.User;
import com.thing.entity.UserType;

import javax.servlet.http.Cookie;
import java.util.List;
import java.util.Objects;

public class SecurityService {
    private UserDao userDao;
    private List<String> tokens;
    private boolean isAdmin;

    public SecurityService(UserDao userDao, List<String> tokens) {
        this.userDao = userDao;
        this.tokens = tokens;
    }

    public boolean isAuthorized(String login, String password) {
        User user = userDao.getByName(login);
        if (user != null) {
            if (Objects.equals(user.getPassword(), password)){

                if (Objects.equals(UserType.ADMIN, user.getUserType())){
                    isAdmin = true;
                }

                return true;
            }
        }

        return false;
    }

    public boolean containsCoockie(Cookie[] cookies){
        if (cookies == null){
            return false;
        }
        for (Cookie cookie : cookies) {
            if ("user-token".equals(cookie.getName())){
                if(tokens.contains(cookie.getValue())){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isAdmin(){
        return isAdmin;
    }
}
