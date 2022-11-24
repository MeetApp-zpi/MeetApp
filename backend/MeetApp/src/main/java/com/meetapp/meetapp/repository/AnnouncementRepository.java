package com.meetapp.meetapp.repository;

import com.meetapp.meetapp.model.Announcement;
import com.meetapp.meetapp.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Integer>,
        JpaSpecificationExecutor<Announcement> {

    Boolean existsByIdIsAndEnrolleesContains(Integer announcementId, Client enrollee);

    List<Announcement> findAllByIsActiveIs(Boolean isActive);
}
