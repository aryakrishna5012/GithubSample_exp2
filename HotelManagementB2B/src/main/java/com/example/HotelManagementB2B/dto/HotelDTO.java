package com.example.HotelManagementB2B.dto;

import lombok.Data;
import java.util.List;

import com.example.HotelManagementB2B.entity.User;

@Data
public class HotelDTO {
    private Long id;
    private String name;
    private String city;
    private boolean approved;
    private List<RoomDTO> rooms;
    private User hotelAdmin;
}
