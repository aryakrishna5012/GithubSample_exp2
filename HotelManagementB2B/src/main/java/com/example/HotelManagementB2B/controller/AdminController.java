package com.example.HotelManagementB2B.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.HotelManagementB2B.entity.Hotel;
import com.example.HotelManagementB2B.entity.User;
import com.example.HotelManagementB2B.repository.HotelRepository;
import com.example.HotelManagementB2B.repository.UserRepository;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final HotelRepository hotelRepository;


    public AdminController(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
      
    }

    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/pending/hotels")
    public List<Hotel> getPendingHotels() {
        return hotelRepository.findByApprovedFalse();
    }

  

    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/approve/hotel/{hotelId}")
    public String approveHotel(@PathVariable Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
        hotel.setApproved(true);
        hotelRepository.save(hotel);
        return "Hotel " + hotel.getName() + " approved successfully!";
    }

    

    
}
