package com.wilker.livraria_api.infrastructure.repository;

import com.wilker.livraria_api.infrastructure.entity.AutorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<AutorEntity, Long> {

    Optional<AutorEntity> findById(Long id);

   @Transactional
    void deleteById(Long id);
}
