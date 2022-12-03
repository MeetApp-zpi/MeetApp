package com.meetapp.meetapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "client_generator")
    @SequenceGenerator(name = "client_generator", sequenceName = "client_sequence", allocationSize = 1)
    private Integer id;

    @NotNull
    @Size(min = 1, max = 320)
    @Email
    @Column(nullable = false, unique = true, length = 320)
    private String email;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String lastName;

    @NotNull
    @Column(nullable = false, length = 255)
    private String profilePicture;

    @NotNull
    @Column(nullable = false)
    private Boolean isDeleted;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "ClientInterest", joinColumns = @JoinColumn(name = "ClientId"), inverseJoinColumns = @JoinColumn(name = "CategoryId"))
    Set<Category> interests;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "ClientPost", joinColumns = @JoinColumn(name = "ClientId", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "PostId", nullable = false, unique = true))
    Set<Post> posts;

    public Client(String email, String firstName, String lastName, String profilePicture) {
        this();

        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profilePicture = profilePicture;
    }

    public Client() {
        id = 0;
        isDeleted = false;
        interests = new HashSet<>();
        posts = new HashSet<>();
    }
}
