package com.dauphine.blogger.controllers;

import com.dauphine.blogger.dto.CreationPostRequest;
import com.dauphine.blogger.dto.UpdatePostRequest;
import com.dauphine.blogger.exceptions.PostNotFoundByIdException;
import com.dauphine.blogger.models.Post;
import com.dauphine.blogger.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
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
    @Operation(summary = "Get all posts", description = "Retrieve all posts or filter by title or content")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Request was successful"),
            @ApiResponse(responseCode = "500", description = "Server encountered an unexpected exception")
    })
    public ResponseEntity<List<Post>> getAllPosts(
            @RequestParam(required = false) String value
    ) {
        List<Post> posts = value == null || value.isBlank()
                ? postService.getAllPosts()
                : postService.getAllByTitleOrContent(value);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get post by id", description = "Retrieve a post by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Request was successful"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Server encountered an unexpected exception")
    })
    public ResponseEntity<Post> getPostById(@PathVariable UUID id) throws PostNotFoundByIdException {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PostMapping
    @Operation(summary = "Create a post", description = "Create a new post")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Request was successful and a new resource was created"),
            @ApiResponse(responseCode = "400", description = "Server couldn't process the request due to a client error"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "Server encountered an unexpected exception")
    })
    public ResponseEntity<Post> createPost(@RequestBody CreationPostRequest request) {
        Post created = postService.createPost(
                request.getTitle(),
                request.getContent(),
                request.getCategoryId()
        );
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a post", description = "Update an existing post by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Request was successful"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Server encountered an unexpected exception")
    })
    public ResponseEntity<Post> updatePost(
            @PathVariable UUID id,
            @RequestBody UpdatePostRequest request) throws PostNotFoundByIdException {
        return ResponseEntity.ok(postService.updatePost(id, request.getTitle(), request.getContent()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a post", description = "Delete a post by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Request was successful"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Server encountered an unexpected exception")
    })
    public ResponseEntity<Boolean> deletePost(@PathVariable UUID id) throws PostNotFoundByIdException {
        return ResponseEntity.ok(postService.deletePost(id));
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Get posts by category", description = "Retrieve all posts for a given category")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Request was successful"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "Server encountered an unexpected exception")
    })
    public ResponseEntity<List<Post>> getPostsByCategory(@PathVariable UUID categoryId) {
        return ResponseEntity.ok(postService.getPostsByCategory(categoryId));
    }
}