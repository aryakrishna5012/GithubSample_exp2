package com.example.HotelManagementB2B.repository;

import com.example.HotelManagementB2B.entity.Room;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByHotelIdAndAvailableTrue(Long hotelId);
}
