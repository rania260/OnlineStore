package com.example.onlinestore.business.services;

import java.util.List;
import java.util.Optional;

import com.example.onlinestore.dao.entities.Product;

public interface IProductService {
    // Retrieves a product by ID.
    public Optional<Product> getProduct(Long id);

    // Retrieves a product by Code.
    public Optional<Product> getProductByCode(String id);

    // Adds a new product.
    public Product addProduct(Product P);

    // Updates an existing product.
    public Product updatePorduct(Product P);

    // Deletes a product by their ID.
    public void deleteProduct(Long id);

    // Retrieves all products.
    public List<Product> getAllProduct();

    // Retrieves all products by category ID
    List<Product> getProductsByCategoryId(Long categoryId);

}