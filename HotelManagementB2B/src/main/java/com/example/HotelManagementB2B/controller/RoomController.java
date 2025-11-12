package com.example.HotelManagementB2B.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.HotelManagementB2B.dto.RoomDTO;
import com.example.HotelManagementB2B.entity.Room;
import com.example.HotelManagementB2B.service.RoomService;

import jakarta.servlet.http.HttpSession;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    private final RoomService roomService;
    
    public RoomController(RoomService roomService) {
    	this.roomService = roomService; 
    	}

    
//    @PreAuthorize("hasRole('HOTEL')")
//    @PostMapping("/add")
//    public RoomDTO addRoom(@RequestBody Room room) { 
//    	return roomService.addRoom(room); 
//    	}
    
    
    @PreAuthorize("hasRole('HOTEL')")
    @PostMapping("/addroom")
    public ResponseEntity<?> addRoom(@RequestBody Room room, @RequestHeader("Authorization") String authHeader, HttpSession session){
    	
        Long hotelId = (Long) session.getAttribute("id");
        if (hotelId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login required");
        }
        
        String token = authHeader.replace("Bearer ", "").trim();

        roomService.addRoom(room ,token, session);
        return ResponseEntity.ok("Room added");
    }

    
    
    
    @PreAuthorize("hasAnyRole('AGENCY','ADMIN')")
    @GetMapping("/hotel/{hotelId}/available")
    public List<RoomDTO> available(@PathVariable Long hotelId) {
    	
    	
    	return roomService.getAvailableRooms(hotelId); 
    	
    	}
}
