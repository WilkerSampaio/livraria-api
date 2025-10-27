package com.wilker.livraria_api.infrastructure.dto.request;

import com.wilker.livraria_api.infrastructure.entity.AutorEntity;

import java.time.LocalDate;
import java.util.List;

public record ObraRequestDTO(

        String nome,
        String descricao,
        LocalDate dataPulicacao,
        List<AutorEntity> autores

) {
}
