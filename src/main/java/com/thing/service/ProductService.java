package com.thing.service;

import com.thing.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    void addNewProduct(Product product);

    void editProduct(Product product);

    void removeProduct(int id);
}
