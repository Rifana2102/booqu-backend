package com.booqu.booqu_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booqu.booqu_backend.model.WebResponse;
import com.booqu.booqu_backend.model.auth.LoginRequest;
import com.booqu.booqu_backend.model.profile.ChangePasswordRequest;
import com.booqu.booqu_backend.model.profile.UserProfileRequest;
import com.booqu.booqu_backend.model.profile.UserProfileResponse;
import com.booqu.booqu_backend.service.UserProfileService;

@RestController
@RequestMapping("/api")
public class ProfileController {

    @Autowired
    UserProfileService userProfileService;

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping(
        path = "/profile",    
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserProfileResponse> getUserProfile(Authentication authentication) {

        UserProfileResponse response = userProfileService.getUserProfile(authentication);

        return WebResponse.<UserProfileResponse>builder()
            .status(    true)
            .messages("Get Profile success")
            .data(response)
            .build();      
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping(
        path = "/profile",   
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserProfileResponse> updateUserProfile(Authentication authentication, @RequestBody UserProfileRequest userProfileRequest) {

        UserProfileResponse response = userProfileService.updateUserProfile(authentication,userProfileRequest);

        return WebResponse.<UserProfileResponse>builder()
            .status(    true)
            .messages("Update Profile success")
            .data(response)
            .build();      
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping(
        path = "/profile/change-password",   
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserProfileResponse> changePassword(Authentication authentication, @RequestBody ChangePasswordRequest changePasswordRequest) {

        UserProfileResponse response = userProfileService.changePassword(authentication,changePasswordRequest);

        return WebResponse.<UserProfileResponse>builder()
            .status(    true)
            .messages("Change Password success")
            .data(response)
            .build();      
    }
}
