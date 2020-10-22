package com.thing.service;

import com.thing.dao.UserDao;
import com.thing.entity.User;
import com.thing.entity.UserType;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class SecurityService {
    private UserDao userDao;
    private List<Session> sessions;

    public SecurityService(UserDao userDao) {
        sessions = new ArrayList<>();
        this.userDao = userDao;
    }

    public Session auth(String login, String password) {
        User user = userDao.getByName(login);
        if (user == null) {
            throw new RuntimeException("Username " + login + " not found!");
        }

        String passwordInHash = DigestUtils.md5Hex(password + user.getSole());

        if (Objects.equals(user.getPassword(), passwordInHash)) {

            Session session = new Session();
            session.setUser(user);
            String token = UUID.randomUUID().toString();
            session.setToken(token);
            session.setExpireTime(LocalDateTime.now().plusHours(2));
            sessions.add(session);

            return session;
        }

        throw new RuntimeException("Session creating failed!");
    }

    public boolean containsCoockie(String token) {
        for (Session session : sessions) {
            if (Objects.equals(token, session.getToken()) && session.getExpireTime().isAfter(LocalDateTime.now())) {
                return true;
            }
        }
        return false;
    }

    public boolean isAdmin(String token) {
        for (Session session : sessions) {
            if (token.equals(session.getToken()) && session.getUser().getUserType().equals(UserType.ADMIN)) {
                return true;
            }
        }
        return false;
    }
}
