package com.booqu.booqu_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booqu.booqu_backend.model.WebResponse;
import com.booqu.booqu_backend.model.auth.LoginRequest;
import com.booqu.booqu_backend.model.auth.LoginResponse;
import com.booqu.booqu_backend.model.auth.RegisterRequest;
import com.booqu.booqu_backend.model.auth.RegisterResponse;
import com.booqu.booqu_backend.service.UserService;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(
        path = "/login",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.login(loginRequest));
    }

    @PostMapping(
        path = "/register",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
        )
    public ResponseEntity<WebResponse<RegisterResponse>> register(@RequestBody RegisterRequest request) {
        WebResponse<RegisterResponse> response = userService.registerUser(request);
    return ResponseEntity.ok(response);
}
}
