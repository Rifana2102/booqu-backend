package com.booqu.booqu_backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.booqu.booqu_backend.model.master.MasterResponse;
import com.booqu.booqu_backend.repository.MasterGenresRepository;
import com.booqu.booqu_backend.entity.MasterGenreEntity;

@Service
public class MasterService {
    @Autowired
    private MasterGenresRepository masterGenresRepository;
    public List<MasterGenreEntity> getGenresList () {
        return masterGenresRepository.findAll();
    }
}