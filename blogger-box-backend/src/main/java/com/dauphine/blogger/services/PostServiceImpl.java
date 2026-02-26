package com.dauphine.blogger.services;

import com.dauphine.blogger.exceptions.EntityNotFoundException;
import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.models.Post;
import com.dauphine.blogger.repositories.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository repository;
    private final CategoryService categoryService;

    public PostServiceImpl(PostRepository repository,
                           CategoryService categoryService) {
        this.repository = repository;
        this.categoryService = categoryService;
    }

    @Override
    public List<Post> getAllPosts() {
        return repository.findAllByOrderByCreatedDateDesc();
    }

    @Override
    public List<Post> getAllByTitle(String title) {
        return repository.findAllByTitleContainingIgnoreCase(title);
    }

    @Override
    public Post getPostById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post", id));
    }

    @Override
    public List<Post> getPostsByCategory(UUID categoryId) {
        return repository.findAllByCategoryId(categoryId);
    }

    @Override
    public Post createPost(String title, String content, UUID categoryId) {
        Category category = categoryService.getCategoryById(categoryId);

        Post post = new Post();
        post.setId(UUID.randomUUID());
        post.setTitle(title);
        post.setContent(content);
        post.setCreatedDate(LocalDateTime.now());
        post.setCategory(category);

        return repository.save(post);
    }

    @Override
    public Post updatePost(UUID id, String title, String content) {
        Post post = getPostById(id);
        post.setTitle(title);
        post.setContent(content);
        return repository.save(post);
    }

    @Override
    public boolean deletePost(UUID id) {
        getPostById(id);
        repository.deleteById(id);
        return true;
    }
}