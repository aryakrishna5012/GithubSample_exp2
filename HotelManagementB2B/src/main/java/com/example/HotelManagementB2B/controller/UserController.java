package com.example.HotelManagementB2B.controller;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.HotelManagementB2B.dto.AuthRequest;
import com.example.HotelManagementB2B.dto.AuthResponse;
import com.example.HotelManagementB2B.dto.UserDTO;
import com.example.HotelManagementB2B.entity.User;
import com.example.HotelManagementB2B.service.UserService;

import jakarta.servlet.http.HttpSession;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) { 
    	this.userService = userService; }

    @PostMapping("/register")
    public UserDTO register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request,HttpSession session) {
        return userService.login(request,session);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public List<UserDTO> allUsers() {
        return userService.getAllUsers();
    }
}
