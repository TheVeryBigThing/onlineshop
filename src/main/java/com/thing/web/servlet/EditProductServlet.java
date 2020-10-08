package com.thing.web.servlet;

import com.thing.entity.Product;
import com.thing.service.ProductService;
import com.thing.service.impl.SecurityService;
import com.thing.web.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class EditProductServlet extends HttpServlet {
    private ProductService productService;
    private SecurityService securityService;
    private int id;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (securityService.containsCoockie(req.getCookies())) {
            if (securityService.isAdmin()){
                id = Integer.parseInt(req.getParameter("id"));

                PageGenerator pageGenerator = PageGenerator.instance();
                Map<String, Object> map = new HashMap<>();
                map.put("action", "edit");

                String page = pageGenerator.getPage("update.html", map);

                resp.getWriter().write(page);
            } else {
                resp.sendRedirect("/guest");
            }
        } else {
            resp.sendRedirect("/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Product product = new Product();
        product.setId(id);
        product.setName(req.getParameter("name"));
        product.setPrice(Double.parseDouble(req.getParameter("price")));
        product.setExpireDate(LocalDate.parse(req.getParameter("expireDate")));

        productService.editProduct(product);

        resp.sendRedirect("/products");
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}
