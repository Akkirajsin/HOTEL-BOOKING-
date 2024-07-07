package com.ums.REPOSITORY;

import com.ums.ENTITY.AppUser;
import com.ums.ENTITY.Property;
import com.ums.ENTITY.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("select r from Review r where r.property=:property and r.appUser=:user")
    Review findByUsername(@Param("property") Property property,@Param("user") AppUser user);

}