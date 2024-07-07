package com.ums.REPOSITORY;

import com.ums.ENTITY.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}