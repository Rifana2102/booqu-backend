package com.booqu.booqu_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booqu.booqu_backend.entity.MasterGenreEntity;
import com.booqu.booqu_backend.model.WebResponse;
import com.booqu.booqu_backend.model.profile.UserProfileResponse;
import com.booqu.booqu_backend.service.MasterService;
import com.booqu.booqu_backend.service.UserProfileService;

@RestController
@RequestMapping("/api")
public class MasterController {

        @Autowired
        private MasterService masterService;

        @GetMapping(
            path = "/masters/genres",    
            produces = MediaType.APPLICATION_JSON_VALUE
        )
        public WebResponse<List<MasterGenreEntity>> getGenres() {

            List<MasterGenreEntity> response = masterService.getAllGenres();

            return WebResponse.<List<MasterGenreEntity>>builder()
                .status(    true)
                .messages("Get Profile success")
                .data(response)
                .build();      
        }
}
