package com.meetapp.meetapp.dto;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record DateTimeDTO(
        @NotEmpty String date, //
        String time) {
    DateTimeDTO(LocalDate date) {
        this(date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), null);
    }
}
