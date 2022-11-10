package com.meetapp.meetapp.dto;

import com.meetapp.meetapp.model.Client;
import com.meetapp.meetapp.model.Location;
import com.meetapp.meetapp.model.Post;
import jakarta.validation.constraints.NotNull;

public record PostDTO(@NotNull Integer id, //
                      @NotNull Client author, //
                      @NotNull Location location, //
                      @NotNull DateTimeDTO creationDate, //
                      @NotNull Boolean isActive) {
    public PostDTO(Post post) {
        this(post.getId(), post.getAuthor(), post.getLocation(), new DateTimeDTO(post.getCreationDate()),
                post.getIsActive());
    }
}
