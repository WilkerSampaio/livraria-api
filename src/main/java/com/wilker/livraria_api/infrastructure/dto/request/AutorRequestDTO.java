package com.wilker.livraria_api.infrastructure.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wilker.livraria_api.infrastructure.enums.SexoEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record AutorRequestDTO(

        @NotBlank(message = "O nome do autor é obrigatório")
        String nome,

        @NotNull(message = "O sexo do autor é obrigatório")
        SexoEnum sexoEnum,

        @NotBlank(message = "O email do autor é obrigatório")
        @Email(message = "O email do autor deve ser válido")
        String email,

        @NotNull(message = "A data é obrigatória")
        @PastOrPresent(message = "A data do nascimento não pode ser futura")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate dataNascimento,

        @NotBlank(message = "O pais do autor é obrigatório")
        String paisOrigem,

        @NotBlank(message = "O CPF do autor é obrigatório")
        String cpf

){
}
