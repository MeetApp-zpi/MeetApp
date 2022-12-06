package com.meetapp.meetapp.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.meetapp.meetapp.model.Category;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record SingleAnnouncementDTO(
        @NotNull @JsonUnwrapped PostDTO post, //
        @NotNull @Size(min = 5, max = 50) String title, //
        @NotNull @Size(min = 1, max = 200) String description, //
        @NotNull Integer enrolled,
        @NotNull Set<Category> categories) {
}
