package com.meetapp.meetapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Chatroom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "chatroom_generator")
    @SequenceGenerator(name = "chatroom_generator", sequenceName = "chatroom_sequence", allocationSize = 1)
    private Integer id;

    @NotNull
    @JoinColumn(nullable = false)
    @ManyToOne(optional = false)
    private Client firstClient;

    @NotNull
    @JoinColumn(nullable = false)
    @ManyToOne(optional = false)
    private Client secondClient;

    @NotNull
    @Column(name = "hasFirstClientRead", nullable = false)
    private Boolean hasFirstClientRead;

    @NotNull
    @Column(name = "hasSecondClientRead", nullable = false)
    private Boolean hasSecondClientRead;

    public Chatroom(Client first, Client second) {
        this();

        this.firstClient = first;
        this.secondClient = second;
    }

    public Chatroom() {
        this.id = 0;
        this.hasFirstClientRead = true;
        this.hasSecondClientRead = true;
    }
}
