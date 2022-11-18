package com.meetapp.meetapp.specification;

import com.meetapp.meetapp.model.Announcement;
import org.springframework.data.jpa.domain.Specification;

public class AnnouncementSpecifications {
    public static Specification<Announcement> hasCategory(Integer category) {
        return (announcement, cq, cb) -> cb.isMember(category, announcement.join("categories").get("categoryId"));
    }

    public static Specification<Announcement> hasLocation(Integer location) {
        return (announcement, cq, cb) -> cb.equal(announcement.get("location").get("id"), location);
    }

    public static Specification<Announcement> titleContains(String searchedPhrase) {
        return (announcement, cq, cb) -> cb.like(announcement.get("title"), "%" + searchedPhrase + "%");
    }

//    static Specification<Announcement> orderBy(Integer orderOption) {
//        if (orderOption == 1) {
//            return (announcement, cq, cb) -> cq.orderBy(cb.asc(announcement.get("enrolled")));
//        } else if (orderOption == 2) {
//            return (announcement, cq, cb) -> cq.orderBy(cb.desc);
//        }
//    }
}
