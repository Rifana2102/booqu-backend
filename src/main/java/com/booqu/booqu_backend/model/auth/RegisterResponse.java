package com.booqu.booqu_backend.model.auth;

public class RegisterResponse {
    private String email;
    private String username;
    private String name;

     public RegisterResponse(String email, String username, String name) {
        this.email = email;
        this.username = username;
        this.name = name;
    }
}
