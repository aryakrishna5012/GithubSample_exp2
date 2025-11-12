package com.example.HotelManagementB2B.security;



import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.example.HotelManagementB2B.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    
    public CustomUserDetailsService(UserRepository userRepository){
    	this.userRepository = userRepository;
    	}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.HotelManagementB2B.entity.User u = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return org.springframework.security.core.userdetails.User.builder()
                .username(u.getUsername())
                .password(u.getPassword())
                .roles(u.getRole().name())
                .build();
    }
}
