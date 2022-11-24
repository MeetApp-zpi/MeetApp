package com.meetapp.meetapp.specification;

import com.meetapp.meetapp.model.Category;
import com.meetapp.meetapp.model.Meeting;
import jakarta.persistence.criteria.Expression;
import lombok.val;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.List;

public class MeetingSpecifications {
    public static Specification<Meeting> hasCategory(List<Integer> categoryIds) {
        return (meeting, cq, cb) -> {
            cq.distinct(true);
            val categorySubquery = cq.subquery(Category.class);
            val categoryObj = categorySubquery.from(Category.class);
            Expression<Collection<Meeting>> categoryPosts = categoryObj.get("posts");
            categorySubquery.select(categoryObj);
            categorySubquery.where(cb.in(categoryObj.get("id")).value(categoryIds), cb.isMember(meeting, categoryPosts));
            return cb.exists(categorySubquery);
        };
    }

    public static Specification<Meeting> hasLocation(List<Integer> locationIds) {
        return (meeting, cq, cb) -> cb.in(meeting.get("location").get("id")).value(locationIds);
    }

    public static Specification<Meeting> titleContains(String searchedPhrase) {
        return (meeting, cq, cb) -> cb.like(cb.lower(meeting.get("title")), "%" + searchedPhrase.toLowerCase() + "%");
    }
}
