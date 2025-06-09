package com.booqu.booqu_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booqu.booqu_backend.entity.MasterGenreEntity;
import com.booqu.booqu_backend.model.master.MasterResponse;

@Repository
public interface MasterGenresRepository extends JpaRepository<MasterGenreEntity, Long>{}
