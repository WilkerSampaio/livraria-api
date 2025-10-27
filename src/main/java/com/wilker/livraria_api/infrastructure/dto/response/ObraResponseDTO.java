package com.wilker.livraria_api.infrastructure.dto.response;

import com.wilker.livraria_api.infrastructure.entity.AutorEntity;

import java.time.LocalDate;
import java.util.List;

public record ObraResponseDTO(

        Long id,
        String nome,
        String descricao,
        LocalDate dataPulicacao,
        List<AutorEntity> autores

) {
}
