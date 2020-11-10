package com.thing.web.filter;

import com.thing.service.SecurityService;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter implements Filter {
    private SecurityService securityService;

    public AuthFilter(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            String token = null;
            for (Cookie cookie : cookies) {
                if ("user-token".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }

            if (token != null && securityService.containsCoockie(token)) {

                if (securityService.isAdmin(token)) {
                    filterChain.doFilter(httpServletRequest, httpServletResponse);
                } else {
                    httpServletResponse.sendRedirect("/guest");
                }

            }

        } else {
            httpServletResponse.sendRedirect("/login");
        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

}

