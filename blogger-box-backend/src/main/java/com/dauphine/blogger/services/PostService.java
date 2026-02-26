package com.dauphine.blogger.services;

import com.dauphine.blogger.models.Post;

import java.util.List;
import java.util.UUID;

public interface PostService {
    List<Post> getAllPosts();
    List<Post> getAllByTitle(String title);
    Post getPostById(UUID id);
    List<Post> getPostsByCategory(UUID categoryId);
    Post createPost(String title, String content, UUID categoryId);
    Post updatePost(UUID id, String title, String content);
    boolean deletePost(UUID id);
}