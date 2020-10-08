package com.thing.dao.jdbc;

import com.thing.dao.ProductDao;
import com.thing.dao.mapper.ProductRowMapper;
import com.thing.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductDao implements ProductDao {
    private static final String URI = "jdbc:sqlite:src\\main\\resources\\shop.db";

    public List<Product> getAllProducts() {
        try (Connection connection = DriverManager.getConnection(URI);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM products;")) {

            List<Product> products = new ArrayList<>();
            ProductRowMapper rowMapper = new ProductRowMapper();
            while (resultSet.next()) {
                products.add(rowMapper.mapRow(resultSet));
            }

            return products;
        } catch (SQLException e) {
            throw new RuntimeException("Get all products failed", e);
        }
    }

    public void addNewProduct(Product product) {
        String inserQuery = "INSERT INTO products (name, price, expireDate) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(URI);
             PreparedStatement preparedStatement = connection.prepareStatement(inserQuery)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setString(3, product.getExpireDate().toString());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Add new product failed", e);
        }
    }

    public void editProduct(Product product) {
        String editQuery = "UPDATE products SET name = ?, price = ?, expireDate = ? WHERE id = ?;";
        try (Connection connection = DriverManager.getConnection(URI);
             PreparedStatement preparedStatement = connection.prepareStatement(editQuery)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setString(3, product.getExpireDate().toString());
            preparedStatement.setInt(4, product.getId());
            preparedStatement.execute();
        } catch (SQLException e){
            throw new RuntimeException("Edit product failed", e);
        }
    }

    public void removeProduct(int id) {
        String removeQuery = "DELETE FROM products WHERE id = ?;";
        try (Connection connection = DriverManager.getConnection(URI);
        PreparedStatement preparedStatement = connection.prepareStatement(removeQuery)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e){
            throw new RuntimeException("Remove product failed", e);
        }
    }

}
