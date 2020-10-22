package com.thing;

import com.thing.dao.jdbc.JdbcProductDao;
import com.thing.dao.jdbc.JdbcUserDao;
import com.thing.service.impl.DefaultProductService;
import com.thing.service.SecurityService;
import com.thing.web.filter.AuthFilter;
import com.thing.web.servlet.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.sqlite.SQLiteDataSource;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class Starter {
    public static void main(String[] args) throws Exception {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:src\\main\\resources\\shop.db");

        JdbcProductDao productDao = new JdbcProductDao(dataSource);
        DefaultProductService productService = new DefaultProductService(productDao);

        JdbcUserDao userDao = new JdbcUserDao(dataSource);
        SecurityService securityService = new SecurityService(userDao);

        GetAllProductsServlet getAllProductsServlet = new GetAllProductsServlet();
        getAllProductsServlet.setProductService(productService);

        RemoveProductServlet removeProductServlet = new RemoveProductServlet();
        removeProductServlet.setProductService(productService);

        AddNewProductServlet addNewProductServlet = new AddNewProductServlet();
        addNewProductServlet.setProductService(productService);

        EditProductServlet editProductServlet = new EditProductServlet();
        editProductServlet.setProductService(productService);

        LoginServlet loginServlet = new LoginServlet();
        loginServlet.setSecurityService(securityService);

        GuestServlet guestServlet = new GuestServlet();
        guestServlet.setProductService(productService);

        AuthFilter authFilter = new AuthFilter(securityService);

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.addServlet(new ServletHolder(getAllProductsServlet), "/products");
        contextHandler.addServlet(new ServletHolder(removeProductServlet), "/products/remove");
        contextHandler.addServlet(new ServletHolder(addNewProductServlet), "/products/add");
        contextHandler.addServlet(new ServletHolder(editProductServlet), "/products/edit");
        contextHandler.addServlet(new ServletHolder(loginServlet), "/login");
        contextHandler.addServlet(new ServletHolder(guestServlet), "/guest");
        contextHandler.addFilter(new FilterHolder(authFilter), "/products/*", EnumSet.of(DispatcherType.REQUEST));

        Server server = new Server(8080);
        server.setHandler(contextHandler);

        server.start();
    }

}
