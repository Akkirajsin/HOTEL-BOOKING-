package com.ums.REPOSITORY;

import com.ums.ENTITY.AppUser;
import com.ums.ENTITY.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
    @Query("select f from Favourite f where f.appUser=:user" )
    public List<Favourite> getFavourite(@Param("user")AppUser user);

}