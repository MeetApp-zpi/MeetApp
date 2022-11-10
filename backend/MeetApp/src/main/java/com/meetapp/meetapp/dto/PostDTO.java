package com.meetapp.meetapp.dto;

import com.meetapp.meetapp.model.Client;
import com.meetapp.meetapp.model.Location;
import jakarta.validation.constraints.NotNull;

public record PostDTO(@NotNull Integer id, //
                      @NotNull Client author, //
                      @NotNull Location location, //
                      @NotNull DateTimeDTO creationDateTime, //
                      @NotNull Boolean isActive) {
}
