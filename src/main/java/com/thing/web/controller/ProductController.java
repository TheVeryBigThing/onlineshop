package com.thing.web.controller;

import com.thing.entity.Product;
import com.thing.service.ProductService;
import com.thing.web.templater.PageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProductController {
    private int id;
    @Autowired
    private ProductService productService;

    @RequestMapping(method = RequestMethod.GET, path = "/products")
    public void showPoducts(HttpServletResponse response) throws IOException {
        List<Product> products = productService.getAllProducts();
        Map<String, Object> map = new HashMap<>();
        map.put("products", products);

        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("products.html", map);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(page);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/guest")
    public void showProductsToGuest(HttpServletResponse response) throws IOException {
        List<Product> products = productService.getAllProducts();
        Map<String, Object> map = new HashMap<>();
        map.put("products", products);

        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("guest.html", map);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(page);
    }

    @RequestMapping(method = RequestMethod.GET, path = "products/add")
    public void showAddProductForm(HttpServletResponse response) throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("action", "add");

        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("update.html", map);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(page);
    }

    @RequestMapping(method = RequestMethod.POST, path = "products/add")
    public void addProduct(HttpServletResponse response, HttpServletRequest request) throws IOException {
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        String expireDate = request.getParameter("expireDate");

        Product product = new Product();
        product.setName(name);
        product.setPrice(Double.parseDouble(price));
        product.setExpireDate(LocalDate.parse(expireDate));

        productService.addNewProduct(product);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect("/products");
    }

    @RequestMapping(method = RequestMethod.GET, path = "products/edit")
    public void showEditProductForm(HttpServletResponse response, HttpServletRequest request) throws IOException {
        id = Integer.parseInt(request.getParameter("id"));
        Map<String, Object> map = new HashMap<>();
        map.put("action", "edit");

        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("update.html", map);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(page);
    }

    @RequestMapping(method = RequestMethod.POST, path = "products/edit")
    public void editProduct(HttpServletResponse response, HttpServletRequest request) throws IOException {
        Product product = new Product();
        product.setId(id);
        product.setName(request.getParameter("name"));
        product.setPrice(Double.parseDouble(request.getParameter("price")));
        product.setExpireDate(LocalDate.parse(request.getParameter("expireDate")));

        productService.editProduct(product);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect("/products");
    }

    @RequestMapping(method = RequestMethod.POST, path = "products/remove")
    public void removeProduct(HttpServletResponse response, HttpServletRequest request) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        productService.removeProduct(id);

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect("/products");
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
