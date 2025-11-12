package com.example.HotelManagementB2B.dto;


import com.example.HotelManagementB2B.entity.Role;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private Role role;
}
