package com.thing.web.controller;

import com.thing.service.SecurityService;
import com.thing.service.Session;
import com.thing.web.templater.PageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    private SecurityService securityService;

    @RequestMapping(method = RequestMethod.GET, path = "/login")
    public void showLoginForm(HttpServletResponse response) throws IOException {
        Map<String, Object> map = new HashMap<>();
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("login.html", map);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(page);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/login")
    public void login(HttpServletResponse response, HttpServletRequest request){
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        try {
            Session session = securityService.auth(login, password);

            Cookie cookie = new Cookie("user-token", session.getToken());
            long timeNowInSeconds = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
            long expireTimeInSeconds = session.getExpireTime().toEpochSecond(ZoneOffset.UTC);

            int activeTimeInSeconds = (int) (expireTimeInSeconds - timeNowInSeconds);

            cookie.setMaxAge(activeTimeInSeconds);
            response.addCookie(cookie);
            response.sendRedirect("/products");
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}
