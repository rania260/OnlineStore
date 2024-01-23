package com.example.onlinestore.web.models.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryForm {
    @NotBlank(message = "Name is required")
    private String name;

}
