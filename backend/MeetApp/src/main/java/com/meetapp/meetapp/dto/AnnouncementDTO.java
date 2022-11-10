package com.meetapp.meetapp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record AnnouncementDTO(@NotNull Integer locationId, //
                              @NotNull @Size(min = 5, max = 50) String title, //
                              @NotNull @Size(min = 1, max = 200) String description, //
                              @NotEmpty Set<Integer> categoryIds, //
                              @NotNull DateTimeDTO creationDateTime) {
}
