package com.example.HotelManagementB2B.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.HotelManagementB2B.entity.Hotel;


import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByCityAndApprovedTrue(String city);

	List<Hotel> findByApprovedFalse();

	Optional<Hotel> findByUsername(String username);

	
}
