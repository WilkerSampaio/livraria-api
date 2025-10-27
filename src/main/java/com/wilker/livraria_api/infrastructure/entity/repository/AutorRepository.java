package com.wilker.livraria_api.infrastructure.entity.repository;

import com.wilker.livraria_api.infrastructure.entity.AutorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Long, AutorEntity> {

    Optional<AutorEntity> findById(Long id);

}
