package com.meetapp.meetapp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import java.util.Set;

@Data
public class AnnouncementDTO {
    @NotNull
    private OAuth2AuthenticationToken webToken;

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
