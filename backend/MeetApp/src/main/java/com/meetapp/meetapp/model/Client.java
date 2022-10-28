package com.meetapp.meetapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Entity
@Data
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
    @Lob
    @Column(nullable = false)
    private Byte[] profilePicture;

    @NotNull
    @Column(nullable = false)
    private Boolean isDeleted;

    @JsonIgnore
    @ManyToMany(targetEntity = Category.class, mappedBy = "clients")
    Set<Category> interests; // TODO add endpoints etc

    @JsonIgnore
    @ManyToMany
    @JoinTable(name="ClientMeeting", joinColumns = @JoinColumn(name="ClientId"), inverseJoinColumns = @JoinColumn(name="MeetingId"))
    Set<Meeting> meetings;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name="ClientEvents", joinColumns = @JoinColumn(name="ClientId"), inverseJoinColumns = @JoinColumn(name="EventId"))
    Set<Event> events;

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
