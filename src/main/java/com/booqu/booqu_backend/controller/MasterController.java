package com.booqu.booqu_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booqu.booqu_backend.entity.MasterGenreEntity;
import com.booqu.booqu_backend.model.WebResponse;
import com.booqu.booqu_backend.service.MasterService;

@RestController
@RequestMapping("/api")
public class MasterController {
    @Autowired
    private MasterService masterService;

    @GetMapping(
        path = "/master/genres",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<MasterGenreEntity>> getGenres () {
        return WebResponse.<List<MasterGenreEntity>>builder()
        .status(true)
        .messages("getGenres successfully")
        .data(masterService.getGenresList())
        .build();
    }
}
