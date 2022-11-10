package com.meetapp.meetapp.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EventDTO(
        @NotNull @JsonUnwrapped PostDTO post,
        @NotNull @Size(min = 5, max = 100) String title, //
        @NotNull @Size(min = 1, max = 10_000) String description, //
        @NotNull Integer enrolled, //
        Integer personQuota, //
        @Size(min = 0, max = 5_000) String schedule, //
        @NotNull DateTimeDTO startDateTime, //
        @NotNull DateTimeDTO endDateTime) {
}
