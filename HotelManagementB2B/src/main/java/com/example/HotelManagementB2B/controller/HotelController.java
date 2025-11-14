package com.example.HotelManagementB2B.controller;

import org.springframework.cache.annotation.CachePut;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.HotelManagementB2B.dto.HotelDTO;
import com.example.HotelManagementB2B.entity.Hotel;
import com.example.HotelManagementB2B.service.HotelService;

import jakarta.servlet.http.HttpSession;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {
    private final HotelService hotelService;
    public HotelController(HotelService hotelService) { 
    	this.hotelService = hotelService; }

  
    @PreAuthorize("hasRole('HOTEL')")
    @PostMapping("/add")
    public ResponseEntity<HotelDTO> addHotel(
            @RequestBody Hotel hotel,
            @RequestHeader("Authorization") String token,
            HttpSession session) {

        
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        HotelDTO savedHotel = hotelService.addHotel(hotel, token, session);
        return ResponseEntity.ok(savedHotel);
    }
    
    
    

    @PreAuthorize("hasAnyRole('AGENCY','ADMIN')")
    @GetMapping("/search")
    public List<HotelDTO> search(@RequestParam String city) { 
    	return hotelService.getHotelsByCity(city); }

    @PreAuthorize("hasAnyRole('ADMIN','HOTEL')")
    @GetMapping("/all")
    public List<HotelDTO> all() { return hotelService.getAllHotels(); }
}
