package com.meetapp.meetapp.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.meetapp.meetapp.model.Category;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record EditEventDTO(
        @NotNull @JsonUnwrapped PostDTO post,
        @NotNull @Size(min = 5, max = 100) String title, //
        @NotNull @Size(min = 1, max = 10_000) String description, //
        @NotNull Integer enrolled, //
        Integer personQuota, //
        @Size(min = 0, max = 5_000) String schedule, //
        @NotNull DateTimeDTO startDateTime, //
        @NotNull DateTimeDTO endDateTime,
        String picture,
        @NotNull Set<Category> categories) {
}
