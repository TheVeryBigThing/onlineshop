package com.thing;

import com.thing.dao.jdbc.JdbcProductDao;
import com.thing.service.impl.DefaultProductService;
import com.thing.web.servlet.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.ArrayList;
import java.util.List;

public class Starter {
    public static void main(String[] args) throws Exception {
        JdbcProductDao productDao = new JdbcProductDao();
        DefaultProductService productService = new DefaultProductService();
        productService.setProductDao(productDao);

        List<String> tokens = new ArrayList<>();

        GetAllProductsServlet getAllProductsServlet = new GetAllProductsServlet();
        getAllProductsServlet.setProductService(productService);

        RemoveProductServlet removeProductServlet = new RemoveProductServlet();
        removeProductServlet.setProductService(productService);

        AddNewProductServlet addNewProductServlet = new AddNewProductServlet(tokens);
        addNewProductServlet.setProductService(productService);

        EditProductServlet editProductServlet = new EditProductServlet();
        editProductServlet.setProductService(productService);

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.addServlet(new ServletHolder(getAllProductsServlet), "/products");
        contextHandler.addServlet(new ServletHolder(removeProductServlet), "/products/remove");
        contextHandler.addServlet(new ServletHolder(addNewProductServlet), "/products/add");
        contextHandler.addServlet(new ServletHolder(editProductServlet), "/products/edit");
        contextHandler.addServlet(new ServletHolder(new LoginServlet(tokens)), "/login");

        Server server = new Server(8080);
        server.setHandler(contextHandler);

        server.start();
    }

}
