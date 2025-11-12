package com.example.HotelManagementB2B.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.HotelManagementB2B.entity.Booking;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByHotelId(Long hotelId);
    List<Booking> findByAgencyId(Long agencyId);
    List<Booking> findByRoomId(Long roomId);
}
