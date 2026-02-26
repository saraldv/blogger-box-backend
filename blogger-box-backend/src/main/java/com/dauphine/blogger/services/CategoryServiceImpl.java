package com.dauphine.blogger.services;

import com.dauphine.blogger.exceptions.EntityNotFoundException;
import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Category> getAllCategories() {
        return repository.findAll();
    }

    @Override
    public List<Category> getAllByName(String name) {
        return repository.findAllByNameContainingIgnoreCase(name);
    }

    @Override
    public Category getCategoryById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category", id));
    }

    @Override
    public Category createCategory(UUID id, String name) {
        Category category = new Category();
        category.setId(id != null ? id : UUID.randomUUID());
        category.setName(name);
        return repository.save(category);
    }

    @Override
    public Category updateCategory(UUID id, String name) {
        Category category = getCategoryById(id);
        category.setName(name);
        return repository.save(category);
    }

    @Override
    public boolean deleteCategory(UUID id) {
        getCategoryById(id);
        repository.deleteById(id);
        return true;
    }
}