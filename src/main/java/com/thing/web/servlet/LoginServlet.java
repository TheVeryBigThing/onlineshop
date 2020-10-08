package com.thing.web.servlet;

import com.thing.service.impl.SecurityService;
import com.thing.web.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class LoginServlet extends HttpServlet {
    private SecurityService securityService;
    private List<String> tokens;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> map = new HashMap<>();
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("login.html", map);

        resp.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (securityService.isAuthorized(login, password)){
            String token = UUID.randomUUID().toString();
            tokens.add(token);
            Cookie cookie = new Cookie("user-token", token);
            resp.addCookie(cookie);
            resp.sendRedirect("/products");
        }

        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    public void setTokens(List<String> tokens) {
        this.tokens = tokens;
    }
}
