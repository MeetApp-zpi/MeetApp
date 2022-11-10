package com.meetapp.meetapp.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MeetingDTO(
        @NotNull @JsonUnwrapped PostDTO post, //
        @NotNull @Size(min = 5, max = 50) String title, //
        @NotNull @Size(min = 1, max = 250) String description, //
        @NotNull Integer enrolled, //
        Integer personQuota, //
        @NotNull DateTimeDTO meetingDateTime) {
}
