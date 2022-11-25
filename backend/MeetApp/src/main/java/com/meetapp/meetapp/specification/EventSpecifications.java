package com.meetapp.meetapp.specification;

import com.meetapp.meetapp.model.Category;
import com.meetapp.meetapp.model.Event;
import jakarta.persistence.criteria.Expression;
import lombok.val;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.List;

public class EventSpecifications {
    public static Specification<Event> hasCategory(List<Integer> categoryIds) {
        return (event, cq, cb) -> {
            cq.distinct(true);
            val categorySubquery = cq.subquery(Category.class);
            val categoryObj = categorySubquery.from(Category.class);
            Expression<Collection<Event>> categoryPosts = categoryObj.get("posts");
            categorySubquery.select(categoryObj);
            categorySubquery.where(cb.in(categoryObj.get("id")).value(categoryIds), cb.isMember(event, categoryPosts));
            return cb.exists(categorySubquery);
        };
    }

    public static Specification<Event> hasLocation(List<Integer> locationIds) {
        return (event, cq, cb) -> cb.in(event.get("location").get("id")).value(locationIds);
    }

    public static Specification<Event> titleContains(String searchedPhrase) {
        return (event, cq, cb) -> cb.like(cb.lower(event.get("title")), "%" + searchedPhrase.toLowerCase() + "%");
    }

    public static Specification<Event> isActive() {
        return (event, cq, cb) -> cb.isTrue(event.get("isActive"));
    }

    public static Specification<Event> isInactive() {
        return (event, cq, cb) -> cb.isFalse(event.get("isActive"));
    }
}
