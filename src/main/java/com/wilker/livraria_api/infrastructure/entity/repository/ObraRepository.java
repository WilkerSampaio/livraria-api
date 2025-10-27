package com.wilker.livraria_api.infrastructure.entity.repository;

import com.wilker.livraria_api.infrastructure.entity.ObraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ObraRepository extends JpaRepository<Long, ObraEntity> {

    Optional<ObraEntity> findById(Long id);
}
