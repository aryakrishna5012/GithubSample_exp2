package com.example.HotelManagementB2B.service;

import org.springframework.stereotype.Service;

import com.example.HotelManagementB2B.dto.RoomDTO;
import com.example.HotelManagementB2B.entity.Hotel;
import com.example.HotelManagementB2B.entity.Room;
import com.example.HotelManagementB2B.entity.User;
import com.example.HotelManagementB2B.repository.HotelRepository;
import com.example.HotelManagementB2B.repository.RoomRepository;
import com.example.HotelManagementB2B.repository.UserRepository;
import com.example.HotelManagementB2B.security.JwtService;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    
    
    public RoomService(RoomRepository roomRepository ,HotelRepository hotelRepository,JwtService jwtService,UserRepository userRepository) { 
    	this.roomRepository = roomRepository;
    	this.hotelRepository = hotelRepository;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public RoomDTO addRoom(Room room ,String token,HttpSession session) {
    	
    	String username = jwtService.extractUsername(token);
    	
    	Long hotelId = (Long) session.getAttribute("hotelId");

     
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found for this user"));
        
        room.setHotel(hotel);
        session.setAttribute("hotelId", hotel.getId());
    	
        Room saved = roomRepository.save(room);
        return mapToDTO(saved);
    }

    public List<RoomDTO> getAvailableRooms(Long hotelId) {
        return roomRepository
        		.findByHotelIdAndAvailableTrue(hotelId)
        		.stream()
        		.map(this::mapToDTO)
        		.collect(Collectors.toList());
    }

    private RoomDTO mapToDTO(Room r){
        RoomDTO d = new RoomDTO(); 
        d.setId(r.getId());
        d.setRoomNumber(r.getRoomNumber());
        d.setType(r.getType()); 
        d.setPrice(r.getPrice()); 
        d.setAvailable(r.isAvailable()); 
        return d;
    }
}
