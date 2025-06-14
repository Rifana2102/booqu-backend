package com.booqu.booqu_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booqu.booqu_backend.entity.MasterGenreEntity;
import com.booqu.booqu_backend.repository.MasterGenreRepository;

@Service
public class MasterService {

    @Autowired
    MasterGenreRepository masterGenreRepository;

    public List<MasterGenreEntity> getAllGenres() {
        return masterGenreRepository.findAll();
    }
}
