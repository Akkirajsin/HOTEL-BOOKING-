package com.ums.REPOSITORY;

import com.ums.ENTITY.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}