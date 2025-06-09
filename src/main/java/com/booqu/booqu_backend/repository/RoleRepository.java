package com.booqu.booqu_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booqu.booqu_backend.entity.MasterRoleEntity;

@Repository
public interface RoleRepository extends JpaRepository<MasterRoleEntity, Long> {
    Optional<MasterRoleEntity> findByCode(String code);
}
