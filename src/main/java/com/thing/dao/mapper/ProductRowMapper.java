package com.thing.dao.mapper;

import com.thing.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ProductRowMapper {
    public Product mapRow(ResultSet resultSet) throws SQLException {
        Product product = new Product();

        product.setId(resultSet.getInt("id"));
        product.setName(resultSet.getString("name"));
        product.setPrice(resultSet.getDouble("price"));
        product.setExpireDate(LocalDate.parse(resultSet.getString("expireDate")));

        return product;
    }
}
