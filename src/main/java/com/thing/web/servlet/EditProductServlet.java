package com.thing.web.servlet;

import com.thing.entity.Product;
import com.thing.service.ProductService;
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
    private int id;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        id = Integer.parseInt(req.getParameter("id"));
        Map<String, Object> map = new HashMap<>();
        map.put("action", "edit");

        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("update.html", map);

        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(page);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Product product = new Product();
        product.setId(id);
        product.setName(req.getParameter("name"));
        product.setPrice(Double.parseDouble(req.getParameter("price")));
        product.setExpireDate(LocalDate.parse(req.getParameter("expireDate")));

        productService.editProduct(product);

        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.sendRedirect("/products");
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
