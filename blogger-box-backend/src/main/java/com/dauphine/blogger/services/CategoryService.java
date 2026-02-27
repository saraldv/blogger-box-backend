package com.dauphine.blogger.services;

import com.dauphine.blogger.models.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<Category> getAllCategories();
    List<Category> getAllByName(String name);
    Category getCategoryById(UUID id);
    Category createCategory(String name);
    Category updateCategory(UUID id, String name);
    boolean deleteCategory(UUID id);
}
