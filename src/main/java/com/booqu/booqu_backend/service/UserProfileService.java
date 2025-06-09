package com.booqu.booqu_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import com.booqu.booqu_backend.entity.UserEntity;
import com.booqu.booqu_backend.model.auth.LoginRequest;
import com.booqu.booqu_backend.model.profile.ChangePasswordRequest;
import com.booqu.booqu_backend.model.profile.UserProfileRequest;
import com.booqu.booqu_backend.model.profile.UserProfileResponse;
import com.booqu.booqu_backend.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserProfileService {

     @Autowired
     UserRepository userRepository;

     @Autowired
     private PasswordEncoder passwordEncoder;

     @Autowired
     private BookLoanMaintenanceService bookLoanMaintenanceService;

     public UserProfileResponse getUserProfile(Authentication authentication) {
          bookLoanMaintenanceService.processAllAutoReturnAndReservations();

          String username = authentication.getName();

          UserEntity currentUser = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

          return UserProfileResponse.builder()
               .email(currentUser.getEmail())
               .username(currentUser.getUsername())
               .name(currentUser.getName())
               .build();
     }

     @Transactional
     public UserProfileResponse updateUserProfile(Authentication authentication, @RequestBody UserProfileRequest userProfileRequest) {
          bookLoanMaintenanceService.processAllAutoReturnAndReservations();

          String username = authentication.getName();

          UserEntity currentUser = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

          currentUser.setName(userProfileRequest.getName());
          userRepository.save(currentUser);

          return UserProfileResponse.builder()
               .email(currentUser.getEmail())
               .username(currentUser.getUsername())
               .name(currentUser.getName())
               .build();
     }

     @Transactional
     public UserProfileResponse changePassword(Authentication authentication, @RequestBody ChangePasswordRequest changePasswordRequest) {
          bookLoanMaintenanceService.processAllAutoReturnAndReservations();
          
          String username = authentication.getName();

          UserEntity currentUser = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

          // Check if old password is correct
          if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), currentUser.getPassword())) {
               throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Old password is incorrect");
          }

          // Optional: Check if new password is same as old
          if (passwordEncoder.matches(changePasswordRequest.getNewPassword(), currentUser.getPassword())) {
               throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password must be different from old password");
          }

          // Save the new password (hashed)
          currentUser.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
          userRepository.save(currentUser);

          return UserProfileResponse.builder()
               .email(currentUser.getEmail())
               .username(currentUser.getUsername())
               .name(currentUser.getName())
               .build();
     }
}
