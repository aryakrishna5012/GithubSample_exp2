package com.example.HotelManagementB2B.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class BookingDTO {
    private Long id;
    private Long hotelId;
    private Long roomId;
    private Long agencyId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String status;
}
