package com.example.HotelManagementB2B.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.HotelManagementB2B.dto.BookingDTO;
import com.example.HotelManagementB2B.entity.Booking;
import com.example.HotelManagementB2B.service.BookingService;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
	
    private final BookingService bookingService;
    
    public BookingController(BookingService bookingService) {
    	this.bookingService = bookingService; }

    @PreAuthorize("hasRole('AGENCY')")
    @PostMapping("/create")
    public BookingDTO create(@RequestBody Booking booking) { 
    	return bookingService.createBooking(booking); }

    @PreAuthorize("hasRole('HOTEL')")
    @GetMapping("/hotel/{hotelId}")
    public List<BookingDTO> hotelBookings(@PathVariable Long hotelId) { 
    	return bookingService.getBookingsByHotel(hotelId); }

    @PreAuthorize("hasRole('AGENCY')")
    @GetMapping("/agency/{agencyId}")
    public List<BookingDTO> agencyBookings(@PathVariable Long agencyId) { 
    	return bookingService.getBookingsByAgency(agencyId); }
}
