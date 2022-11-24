package com.meetapp.meetapp.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AnnouncementDTO(
        @NotNull @JsonUnwrapped PostDTO post, //
        @NotNull @Size(min = 5, max = 50) String title, //
        @NotNull @Size(min = 1, max = 200) String description, //
        @NotNull Integer enrolled) {
}
