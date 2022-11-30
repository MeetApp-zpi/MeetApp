package com.meetapp.meetapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "message_generator")
    @SequenceGenerator(name = "message_generator", sequenceName = "message_sequence", allocationSize = 1)
    private Integer id;

    @NotNull
    @JoinColumn(nullable = false)
    @ManyToOne(optional = false)
    private Chatroom room;

    @NotNull
    @JoinColumn(nullable = false)
    @ManyToOne(optional = false)
    private Client author;

    @NotNull
    @Column(name = "content", nullable = false)
    private String content;

    @NotNull
    @Column(name = "hasBeenRead", nullable = false)
    private Boolean hasBeenRead;

    @NotNull
    @Basic
    @Column(nullable = false)
    private Instant creationDate;

    public Message(Chatroom room, Client author, String content) {
        this();

        this.room = room;
        this.author = author;
        this.content = content;
    }

    public Message() {
        this.id = 0;
        this.hasBeenRead = false;
        this.creationDate = Instant.now();
    }
}
