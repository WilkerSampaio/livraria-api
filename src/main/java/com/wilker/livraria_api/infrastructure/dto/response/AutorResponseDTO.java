package com.wilker.livraria_api.infrastructure.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wilker.livraria_api.infrastructure.enums.SexoEnum;

import java.time.LocalDate;
import java.util.List;

public record AutorResponseDTO (

        Long id,
        String nome,
        SexoEnum sexoEnum,
        String email,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate dataNascimento,
        String paisOrigem,
        String cpf,
        List<ObraResponseDTO> obras

){
}
