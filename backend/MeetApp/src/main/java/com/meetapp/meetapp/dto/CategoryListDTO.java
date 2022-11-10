package com.meetapp.meetapp.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;

@Data
public class CategoryListDTO {
    @NotEmpty
    private Set<Integer> categories;
}
