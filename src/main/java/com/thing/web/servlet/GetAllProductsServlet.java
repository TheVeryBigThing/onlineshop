package com.thing.web.servlet;

import com.thing.entity.Product;
import com.thing.service.ProductService;
import com.thing.web.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetAllProductsServlet extends HttpServlet {
    private ProductService productService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Product> products = productService.getAllProducts();
        Map<String, Object> map = new HashMap<>();
        map.put("products", products);

        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("products.html", map);

        resp.getWriter().write(page);

    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
