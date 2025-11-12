package com.example.HotelManagementB2B.service;

import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.HotelManagementB2B.dto.AuthRequest;
import com.example.HotelManagementB2B.dto.AuthResponse;
import com.example.HotelManagementB2B.dto.UserDTO;
import com.example.HotelManagementB2B.entity.User;
import com.example.HotelManagementB2B.repository.UserRepository;
import com.example.HotelManagementB2B.security.JwtService;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public UserDTO register(User user) {
        if (userRepository.existsByUsername(user.getUsername()))throw new RuntimeException("Username exists");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User saved = userRepository.save(user);
        return mapToDTO(saved);
    }

    public AuthResponse login(AuthRequest request,HttpSession session) {
    	
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        session.setAttribute("id", user.getId());
        session.setAttribute("username", user.getUsername());
        session.setAttribute("role", user.getRole());
        
        String token = jwtService.generateToken(user);
        return new AuthResponse(token ,session.getId());
    }

    public List<UserDTO> getAllUsers() {
        return userRepository
        		.findAll()
        		.stream()
        		.map(this::mapToDTO)
        		.collect(Collectors.toList());
    }

    private UserDTO mapToDTO(User u) {
        UserDTO dto = new UserDTO();
        dto.setId(u.getId());
        dto.setUsername(u.getUsername());
        dto.setEmail(u.getEmail());
        dto.setRole(u.getRole());
        return dto;
    }
}
