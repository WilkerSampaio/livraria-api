package com.wilker.livraria_api.infrastructure.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;
import java.util.Set;

public record ObraRequestDTO(

        @NotBlank(message = "O nome da obra é obrigatório")
        String nome,

        @NotBlank(message = "A descrição da obra é obrigatório")
        String descricao,

        @NotNull(message = "A data é obrigatória")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        @PastOrPresent(message = "A data de publicação não pode ser futura")
        LocalDate dataPublicacao,

        @NotEmpty(message = "A obra deve ter pelo menos um autor")
        Set<Long> autoresIds

) {
}
