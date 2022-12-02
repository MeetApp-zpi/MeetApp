package com.meetapp.meetapp.repository;

import com.meetapp.meetapp.model.Chatroom;
import com.meetapp.meetapp.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatroomRepository extends JpaRepository<Chatroom, Integer> {
    Boolean existsChatroomByFirstClientAndSecondClient(Client firstClient, Client secondClient);

    Chatroom findChatroomByFirstClientAndSecondClient(Client firstClient, Client secondClient);

    List<Chatroom> findAllByFirstClientOrSecondClient(Client firstClient, Client secondClient);

    Boolean existsChatroomByFirstClientAndHasFirstClientRead(Client firstClient, Boolean hasFirstClientRead);

    Boolean existsChatroomBySecondClientAndHasSecondClientRead(Client secondClient, Boolean hasSecondClientRead);

    List<Chatroom> findAllByFirstClientAndAndHasFirstClientRead(Client firstClient, Boolean hasFirstClientRead);
    List<Chatroom> findAllBySecondClientAndAndHasSecondClientRead(Client secondClient, Boolean hasSecondClientRead);
}
