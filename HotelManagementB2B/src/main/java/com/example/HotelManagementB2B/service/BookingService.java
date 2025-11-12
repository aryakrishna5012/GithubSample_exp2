package com.example.HotelManagementB2B.service;

import org.springframework.stereotype.Service;

import com.example.HotelManagementB2B.dto.BookingDTO;
import com.example.HotelManagementB2B.entity.Booking;
import com.example.HotelManagementB2B.entity.Room;
import com.example.HotelManagementB2B.repository.BookingRepository;
import com.example.HotelManagementB2B.repository.RoomRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;

    public BookingService(BookingRepository bookingRepository, RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
    }

    public BookingDTO createBooking(Booking booking) {
        Room room = booking.getRoom();
        room.setAvailable(false);
        roomRepository.save(room);
        Booking saved = bookingRepository.save(booking);
        return mapToDTO(saved);
    }

    public List<BookingDTO> getBookingsByHotel(Long hotelId) {
        return bookingRepository
        		.findByHotelId(hotelId)
        		.stream().map(this::mapToDTO)
        		.collect(Collectors.toList());
    }

    

    private BookingDTO mapToDTO(Booking b) {
        BookingDTO dto = new BookingDTO();
        dto.setId(b.getId());
        dto.setHotelId(b.getHotel() != null ? b.getHotel().getId() : null);
        dto.setRoomId(b.getRoom() != null ? b.getRoom().getId() : null);
        dto.setAgencyId(b.getAgency() != null ? b.getAgency().getId() : null);
        dto.setCheckInDate(b.getCheckInDate());
        dto.setCheckOutDate(b.getCheckOutDate());
        dto.setStatus(b.getStatus());
        return dto;
    }

	public List<BookingDTO> getBookingsByAgency(Long agencyId) {
		return bookingRepository
				.findByAgencyId(agencyId)
				.stream().map(this::mapToDTO)
				.collect(Collectors.toList());
		
	}
}
