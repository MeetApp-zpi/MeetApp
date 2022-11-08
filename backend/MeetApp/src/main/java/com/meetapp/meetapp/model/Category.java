package com.meetapp.meetapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "category_generator")
    @SequenceGenerator(name = "category_generator", sequenceName = "category_sequence", allocationSize = 1)
    private Integer id;

    @NotNull
    @Size(min = 1, max = 30)
    @Column(nullable = false, unique = true, length = 30)
    private String name;

    @JsonIgnore
    @ManyToMany(targetEntity = Client.class, mappedBy = "interests")
    Set<Client> clients;

    public Category(String name) {
        id = 0;
        this.name = name;
    }

    public Category() {
        id = 0;
        name = "";
    }
}
