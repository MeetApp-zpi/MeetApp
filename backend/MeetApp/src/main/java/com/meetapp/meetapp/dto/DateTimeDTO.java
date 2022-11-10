package com.meetapp.meetapp.dto;

import jakarta.validation.constraints.NotEmpty;

public record DateTimeDTO(@NotEmpty String date, //
                          @NotEmpty String time) {
}
