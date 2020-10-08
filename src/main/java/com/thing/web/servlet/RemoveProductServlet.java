package com.thing.web.servlet;

import com.thing.service.ProductService;
import com.thing.service.impl.SecurityService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveProductServlet extends HttpServlet {
    private ProductService productService;
    private SecurityService securityService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (securityService.containsCoockie(req.getCookies())){
            if (securityService.isAdmin()) {
                int id = Integer.parseInt(req.getParameter("id"));
                productService.removeProduct(id);

                resp.sendRedirect("/products");
            } else {
                resp.sendRedirect("/guest");
            }
        } else {
            resp.sendRedirect("/login");
        }

    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}
