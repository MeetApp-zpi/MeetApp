package com.meetapp.meetapp.dto;

import jakarta.persistence.Basic;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Data
public class EventCreationDTO {
    @NotNull
    private Integer locationId;

    @NotNull
    @Size(min = 5, max = 100)
    private String title;

    @NotNull
    @Size(min = 1, max = 10_000)
    private String description;

    @NotEmpty
    private Set<Integer> categoryIds;

    private Integer personQuota;

    @NotEmpty
    @Basic
    @Future
    private String startDate;

    @NotEmpty
    @Basic
    @Future
    private String endDate;

    @Size(min = 0, max = 5_000)
    private String schedule;

    private MultipartFile picture;
}
