package com.meetapp.meetapp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class AnnouncementCreationDTO {
    @NotNull
    private Integer locationId;

    @NotNull
    @Size(min = 5, max = 50)
    private String title;

    @NotNull
    @Size(min = 1, max = 200)
    private String description;

    @NotEmpty
    private Set<Integer> categoryIds;
}
