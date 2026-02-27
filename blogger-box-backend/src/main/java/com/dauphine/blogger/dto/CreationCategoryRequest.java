package com.dauphine.blogger.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class CreationCategoryRequest {

    @Schema(description = "Name of the category", required = true, example = "Technology")
    private String name;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}