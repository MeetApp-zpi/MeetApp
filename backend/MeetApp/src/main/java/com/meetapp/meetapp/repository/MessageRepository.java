package com.meetapp.meetapp.repository;

import com.meetapp.meetapp.model.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findAllByRoomId(Integer roomId, Pageable pageable);
}
