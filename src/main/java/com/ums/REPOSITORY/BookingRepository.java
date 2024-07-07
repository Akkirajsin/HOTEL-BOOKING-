package com.ums.REPOSITORY;

import com.ums.ENTITY.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}