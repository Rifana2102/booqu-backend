package com.booqu.booqu_backend.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.booqu.booqu_backend.entity.BookStockEntity;
import com.booqu.booqu_backend.entity.MasterRoleEntity;
import com.booqu.booqu_backend.entity.SessionEntity;
import com.booqu.booqu_backend.entity.UserEntity;
import com.booqu.booqu_backend.model.WebResponse;
import com.booqu.booqu_backend.model.auth.LoginRequest;
import com.booqu.booqu_backend.model.auth.LoginResponse;
import com.booqu.booqu_backend.model.auth.RegisterRequest;
import com.booqu.booqu_backend.model.auth.RegisterResponse;
import com.booqu.booqu_backend.model.auth.UserResponse;
import com.booqu.booqu_backend.repository.RoleRepository;
import com.booqu.booqu_backend.repository.SessionRepository;
import com.booqu.booqu_backend.repository.UserRepository;
import com.booqu.booqu_backend.security.JwtUtil;
import com.booqu.booqu_backend.security.SecurityConstants;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final SessionRepository sessionRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private BookLoanMaintenanceService bookLoanMaintenanceService;

    public LoginResponse login(LoginRequest loginRequest) {
        bookLoanMaintenanceService.processAllAutoReturnAndReservations();

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);

        UserEntity user = userRepository.findByUsername(loginRequest.getUsername())
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Date now = new Date();      
        Date expiration = new Date(now.getTime() + SecurityConstants.JWTexpiration);
  
        SessionEntity session = SessionEntity.builder()
                .token(token)
                .expiredAt(expiration)
                .user(user)
                .createdBy(loginRequest.getUsername())
                .createdAt(new Date())
                .updatedBy(loginRequest.getUsername())
                .updatedAt(new Date())
                .build();

        sessionRepository.save(session);

        UserResponse userResponse = UserResponse.builder()
            .username(user.getUsername())
            .email(user.getEmail())
            .name(user.getName())
            .build();

        return LoginResponse.builder()
            .token(token)
            .tokenType("Bearer")
            .expiresIn(jwtUtil.getExpirationDateFromToken(token))
            .user(userResponse)
            .build();
    }

    @Transactional
    public RegisterResponse registerUser(RegisterRequest request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        MasterRoleEntity userRole = roleRepository.findByCode("USER")
                .orElseThrow(() -> new RuntimeException("Default role not found"));

        UserEntity user = UserEntity.builder()
                .name(request.getName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(userRole)
                .isActive(true)
                .createdBy(request.getUsername())
                .createdAt(new Date())
                .updatedBy(request.getUsername())
                .updatedAt(new Date())
                .build();

        userRepository.save(user);

        return RegisterResponse.builder()
            .email(user.getEmail())
            .username(user.getUsername())
            .name(user.getName())
            .build();
    }
}
