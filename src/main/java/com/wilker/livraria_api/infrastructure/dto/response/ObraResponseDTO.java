package com.wilker.livraria_api.infrastructure.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.Set;

public record ObraResponseDTO(

        Long id,
        String nome,
        String descricao,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate dataPublicacao,
        Set<Long> autoresIds

) {
}
