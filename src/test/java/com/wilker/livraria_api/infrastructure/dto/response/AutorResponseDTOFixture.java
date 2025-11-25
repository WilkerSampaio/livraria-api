package com.wilker.livraria_api.infrastructure.dto.response;

import com.wilker.livraria_api.infrastructure.enums.SexoEnum;

import java.time.LocalDate;

public class AutorResponseDTOFixture {

    public static AutorResponseDTO build(    Long id,
                                      String nome,
                                      SexoEnum sexoEnum,
                                      String email,
                                      LocalDate dataNascimento,
                                      String paisOrigem,
                                      String cpf){
        return new AutorResponseDTO(id, nome, sexoEnum, email, dataNascimento, paisOrigem, cpf);
    }
}
