package com.thing.dao;

import com.thing.entity.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getAllProducts();

    void addNewProduct(Product product);

    void editProduct(Product product);

    void removeProduct(int id);
}
