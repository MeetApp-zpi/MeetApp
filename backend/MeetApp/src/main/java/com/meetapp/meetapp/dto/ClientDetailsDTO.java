package com.meetapp.meetapp.dto;

import com.meetapp.meetapp.model.Category;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record ClientDetailsDTO(
        @NotNull Integer id,
        @NotNull String email,
        @NotNull String firstName,
        @NotNull String lastName,
        @NotNull String profilePicture,
        @NotNull Boolean isDeleted,
        Set<Category> interests
) {

};
