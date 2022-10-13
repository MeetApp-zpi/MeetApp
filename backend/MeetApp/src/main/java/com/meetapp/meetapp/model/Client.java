package com.meetapp.meetapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "client_generator")
    @SequenceGenerator(name = "client_generator", sequenceName = "client_sequence", allocationSize = 1)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Lob
    private Byte[] profilePicture;

    @Column(nullable = false)
    private Boolean isDeleted;

    @JsonIgnore
    @ManyToMany(targetEntity = Category.class, mappedBy = "clients")
    Set<Category> interests; // TODO add endpoints etc

    public Client(String email, String firstName, String lastName) {
        id = 0;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        isDeleted = false;
    }

    public Client() {
        id = 0;
        email = "";
        firstName = "";
        lastName = "";
        isDeleted = false;
    }
}
