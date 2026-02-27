package com.dauphine.blogger.controllers;

import com.dauphine.blogger.dto.CreationCategoryRequest;
import com.dauphine.blogger.dto.UpdateCategoryRequest;
import com.dauphine.blogger.exceptions.CategoryNotFoundByIdException;
import com.dauphine.blogger.models.Category;
import com.dauphine.blogger.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/categories")
@Tag(name = "Category API", description = "Endpoints for managing categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @Operation(summary = "Get all categories", description = "Retrieve all categories or filter by name")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Request was successful"),
            @ApiResponse(responseCode = "500", description = "Server encountered an unexpected exception")
    })
    public ResponseEntity<List<Category>> getAllCategories(
            @RequestParam(required = false) String name
    ) {
        List<Category> categories = name == null || name.isBlank()
                ? categoryService.getAllCategories()
                : categoryService.getAllByName(name);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get category by id", description = "Retrieve a category by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Request was successful"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Server encountered an unexpected exception")
    })
    public ResponseEntity<Category> getCategoryById(@PathVariable UUID id) throws CategoryNotFoundByIdException {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PostMapping
    @Operation(summary = "Create a category", description = "Create a new category")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Request was successful and a new resource was created"),
            @ApiResponse(responseCode = "400", description = "Server couldn't process the request due to a client error"),
            @ApiResponse(responseCode = "500", description = "Server encountered an unexpected exception")
    })
    public ResponseEntity<Category> createCategory(@RequestBody CreationCategoryRequest request) {
        Category created = categoryService.createCategory(request.getId(), request.getName());
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a category", description = "Update an existing category by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Request was successful"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Server encountered an unexpected exception")
    })
    public ResponseEntity<Category> updateCategory(
            @PathVariable UUID id,
            @RequestBody UpdateCategoryRequest request) throws CategoryNotFoundByIdException {
        return ResponseEntity.ok(categoryService.updateCategory(id, request.getName()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a category", description = "Delete a category by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Request was successful"),
            @ApiResponse(responseCode = "404", description = "Resource not found"),
            @ApiResponse(responseCode = "500", description = "Server encountered an unexpected exception")
    })
    public ResponseEntity<Boolean> deleteCategory(@PathVariable UUID id) throws CategoryNotFoundByIdException {
        return ResponseEntity.ok(categoryService.deleteCategory(id));
    }
}