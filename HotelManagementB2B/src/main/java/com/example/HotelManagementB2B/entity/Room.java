package com.example.HotelManagementB2B.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomNumber;
    private String type;
    private Double price;
    private boolean available = true;

    @ManyToOne
    private Hotel hotel;
}
