package com.thing;

import com.thing.dao.jdbc.JdbcProductDao;
import com.thing.dao.jdbc.JdbcUserDao;
import com.thing.entity.User;
import com.thing.service.impl.DefaultProductService;
import com.thing.service.impl.SecurityService;
import com.thing.web.servlet.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.ArrayList;
import java.util.List;

public class Starter {
    public static void main(String[] args) throws Exception {
        JdbcProductDao productDao = new JdbcProductDao();
        DefaultProductService productService = new DefaultProductService(productDao);

        List<String> tokens = new ArrayList<>();
        JdbcUserDao userDao = new JdbcUserDao();
        SecurityService securityService = new SecurityService(userDao, tokens);


        GetAllProductsServlet getAllProductsServlet = new GetAllProductsServlet();
        getAllProductsServlet.setProductService(productService);
        getAllProductsServlet.setSecurityService(securityService);

        RemoveProductServlet removeProductServlet = new RemoveProductServlet();
        removeProductServlet.setProductService(productService);
        removeProductServlet.setSecurityService(securityService);

        AddNewProductServlet addNewProductServlet = new AddNewProductServlet(tokens);
        addNewProductServlet.setProductService(productService);
        addNewProductServlet.setSecurityService(securityService);

        EditProductServlet editProductServlet = new EditProductServlet();
        editProductServlet.setProductService(productService);
        editProductServlet.setSecurityService(securityService);

        LoginServlet loginServlet = new LoginServlet();
        loginServlet.setTokens(tokens);
        loginServlet.setSecurityService(securityService);

        GuestServlet guestServlet = new GuestServlet();
        guestServlet.setProductService(productService);

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.addServlet(new ServletHolder(getAllProductsServlet), "/products");
        contextHandler.addServlet(new ServletHolder(removeProductServlet), "/products/remove");
        contextHandler.addServlet(new ServletHolder(addNewProductServlet), "/products/add");
        contextHandler.addServlet(new ServletHolder(editProductServlet), "/products/edit");
        contextHandler.addServlet(new ServletHolder(loginServlet), "/login");
        contextHandler.addServlet(new ServletHolder(guestServlet), "/guest");

        Server server = new Server(8080);
        server.setHandler(contextHandler);

        server.start();
    }

}
