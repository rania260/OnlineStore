package com.example.onlinestore.business.services;

import com.example.onlinestore.dao.entities.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    // Retrieves a category by ID.
    Optional<Category> getCategory(Long id);

    // Adds a new category.
    Category addCategory(Category category);

    // Updates an existing category.
    Category updateCategory(Category category);

    // Deletes a category by its ID.
    void deleteCategory(Long id);

    // Retrieves all categories.
    List<Category> getAllCategories();
}