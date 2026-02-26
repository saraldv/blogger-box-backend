package com.dauphine.blogger.controllers;

import com.dauphine.blogger.dto.CreationPostRequest;
import com.dauphine.blogger.dto.UpdatePostRequest;
import com.dauphine.blogger.models.Post;
import com.dauphine.blogger.services.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/posts")
@Tag(name = "Post API", description = "Endpoints for managing posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable UUID id) {
        return postService.getPostById(id);
    }

    @GetMapping("/category/{categoryId}")
    public List<Post> getPostsByCategory(@PathVariable UUID categoryId) {
        return postService.getPostsByCategory(categoryId);
    }

    @PostMapping
    public Post createPost(@RequestBody CreationPostRequest request) {
        return postService.createPost(
                request.getTitle(),
                request.getContent(),
                request.getCategoryId()
        );
    }

    @PutMapping("/{id}")
    public Post updatePost(
            @PathVariable UUID id,
            @RequestBody UpdatePostRequest request) {

        return postService.updatePost(
                id,
                request.getTitle(),
                request.getContent()
        );
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable UUID id) {
        postService.deletePost(id);
    }
}
