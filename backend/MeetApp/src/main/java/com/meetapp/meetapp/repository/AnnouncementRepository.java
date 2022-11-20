package com.meetapp.meetapp.repository;

import com.meetapp.meetapp.model.Announcement;
import com.meetapp.meetapp.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {
    Boolean existsByIdIsAndEnrolleesContains(Integer announcementId, Client enrollee);
}
