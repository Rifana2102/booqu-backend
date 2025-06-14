package com.booqu.booqu_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booqu.booqu_backend.model.WebResponse;
import com.booqu.booqu_backend.model.book.BooksResponse;
import com.booqu.booqu_backend.model.notification.NotificationResponse;
import com.booqu.booqu_backend.model.profile.UserProfileResponse;
import com.booqu.booqu_backend.service.NotificationService;

@RestController
@RequestMapping("/api")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping(
        path = "/notification",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<NotificationResponse>> getNotification(Authentication authentication) {

        List<NotificationResponse> response = notificationService.getNotification(authentication);

        return WebResponse.<List<NotificationResponse>>builder()
            .status(    true)
            .messages("Get Profile success")
            .data(response)
            .build();
    }
}
