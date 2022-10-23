package com.meetapp.meetapp.repository;

import com.meetapp.meetapp.model.Announcement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository extends CrudRepository<Announcement, Integer> {
    Announcement findAnnouncementById(Integer announcementId);
}
