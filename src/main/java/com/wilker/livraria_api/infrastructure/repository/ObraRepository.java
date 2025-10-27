package com.wilker.livraria_api.infrastructure.repository;

import com.wilker.livraria_api.infrastructure.entity.ObraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ObraRepository extends JpaRepository<ObraEntity, Long> {

    Optional<ObraEntity> findById(Long id);
}
