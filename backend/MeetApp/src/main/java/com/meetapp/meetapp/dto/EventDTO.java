package com.meetapp.meetapp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record EventDTO(@NotNull Integer locationId, //
                       @NotNull @Size(min = 5, max = 100) String title, //
                       @NotNull @Size(min = 1, max = 10_000) String description, //
                       @NotEmpty Set<Integer> categoryIds, //
                       Integer personQuota, //
                       @Size(min = 0, max = 5_000) String schedule, //
                       @NotNull DateTimeDTO creationDateTime, //
                       @NotNull DateTimeDTO startDateTime, //
                       @NotNull DateTimeDTO endDateTime) {
}
