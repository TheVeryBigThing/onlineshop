package com.thing.web.servlet;

import com.thing.service.SecurityService;
import com.thing.service.Session;
import com.thing.web.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class LoginServlet extends HttpServlet {
    private SecurityService securityService;

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

        try {
            Session session = securityService.auth(login, password);

            Cookie cookie = new Cookie("user-token", session.getToken());
            cookie.setMaxAge((int) session.getExpireTime().toEpochSecond(ZoneOffset.UTC));
            resp.addCookie(cookie);
            resp.sendRedirect("/products");
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}
