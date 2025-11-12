package com.example.HotelManagementB2B.dto;

import lombok.Data;

@Data
public class RoomDTO {
    private Long id;
    private String roomNumber;
    private String type;
    private Double price;
    private boolean available;
   
}
