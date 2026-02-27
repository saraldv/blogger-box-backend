package com.dauphine.blogger.services;

import com.dauphine.blogger.exceptions.CategoryNotFoundByIdException;
import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.repositories.CategoryRepository;
import com.dauphine.blogger.repositories.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final PostRepository postRepository;

    public CategoryServiceImpl(CategoryRepository repository, PostRepository postRepository) {
        this.repository = repository;
        this.postRepository = postRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return repository.findAll();
    }

    @Override
    public List<Category> getAllByName(String name) {
        return repository.findAllLikeName(name);
    }

    @Override
    public Category getCategoryById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundByIdException(id));
    }

    @Override
    public Category createCategory(String name) {
        Category category = new Category();
        category.setId(UUID.randomUUID());
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
        if (!postRepository.findAllByCategoryId(id).isEmpty()) {
            throw new IllegalArgumentException("Cannot delete category with existing posts");
        }
        repository.deleteById(id);
        return true;
    }
}