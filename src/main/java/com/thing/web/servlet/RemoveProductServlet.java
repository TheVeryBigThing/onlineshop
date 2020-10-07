package com.thing.web.servlet;

import com.thing.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveProductServlet extends HttpServlet {
    private ProductService productService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        productService.removeProduct(id);

        resp.sendRedirect("/products");
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
