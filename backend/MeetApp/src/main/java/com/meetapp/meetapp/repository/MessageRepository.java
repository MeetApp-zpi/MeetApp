package com.meetapp.meetapp.repository;

import com.meetapp.meetapp.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
}
