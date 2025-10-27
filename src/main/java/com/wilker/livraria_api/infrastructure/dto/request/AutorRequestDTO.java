package com.wilker.livraria_api.infrastructure.dto.request;

import com.wilker.livraria_api.infrastructure.entity.ObraEntity;
import com.wilker.livraria_api.infrastructure.enums.SexoEnum;

import java.time.LocalDate;
import java.util.List;

public record AutorRequestDTO(

        String nome,
        SexoEnum sexoEnum,
        String email,
        LocalDate dataNascimento,
        String paisOrigem,
        String cpf,
        List<ObraEntity> obras
){
}
