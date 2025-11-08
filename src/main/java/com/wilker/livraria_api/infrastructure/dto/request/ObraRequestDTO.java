package com.wilker.livraria_api.infrastructure.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.util.Set;

public record ObraRequestDTO(

        String nome,
        String descricao,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate dataPublicacao,
        @NotEmpty(message = "A obra deve ter pelo menos um autor")
        Set<Long> autoresIds

) {
}
