package com.example.HotelManagementB2B.service;


import org.springframework.stereotype.Service;

import com.example.HotelManagementB2B.dto.HotelDTO;
import com.example.HotelManagementB2B.entity.Hotel;
import com.example.HotelManagementB2B.entity.User;
import com.example.HotelManagementB2B.repository.HotelRepository;
import com.example.HotelManagementB2B.repository.UserRepository;
import com.example.HotelManagementB2B.security.JwtService;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelService {
    private final HotelRepository hotelRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    public HotelService(HotelRepository hotelRepository,JwtService jwtService,UserRepository userRepository) {
    	this.hotelRepository = hotelRepository;
    	this.jwtService = jwtService;
    	this.userRepository = userRepository;
    	}

    public HotelDTO addHotel(Hotel hotel,String token,HttpSession session) {
    	
    	String username = jwtService.extractUsername(token);
    	
    	User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    	
        hotel.setHotelAdmin(user);
        session.setAttribute("hotelId", hotel.getId());
    	
        Hotel saved = hotelRepository.save(hotel);
        return mapToDto(saved);
    }
    
    
    
    
    
    

    public List<HotelDTO> getHotelsByCity(String city) {
        return hotelRepository
        		.findByCityAndApprovedTrue(city)
        		.stream().map(this::mapToDto)
        		.collect(Collectors.toList());
    }

    public List<HotelDTO> getAllHotels() {
        return hotelRepository
        		.findAll()
        		.stream()
        		.map(this::mapToDto)
        		.collect(Collectors.toList());
    }

    private HotelDTO mapToDto(Hotel h){
        HotelDTO d = new HotelDTO();
        d.setId(h.getId()); 
        d.setName(h.getName()); 
        d.setCity(h.getCity()); 
        d.setApproved(h.isApproved());
        d.setHotelAdmin(h.getHotelAdmin());
        return d;
    }
}
