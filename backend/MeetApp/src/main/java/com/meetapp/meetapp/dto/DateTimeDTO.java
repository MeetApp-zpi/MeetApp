package com.meetapp.meetapp.dto;

import jakarta.validation.constraints.NotEmpty;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public record DateTimeDTO(
        @NotEmpty String date, //
        String time) {
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy").withZone(
            TimeZone.getTimeZone("UTC").toZoneId());
    private static final DateTimeFormatter timeFormatter =
            DateTimeFormatter.ofPattern("HH:mm").withZone(TimeZone.getTimeZone("UTC").toZoneId());

    DateTimeDTO(LocalDate date) {
        this(date.format(dateFormatter), null);
    }

    public DateTimeDTO(Instant instant) {
        this(dateFormatter.format(instant), timeFormatter.format(instant));
    }
}
