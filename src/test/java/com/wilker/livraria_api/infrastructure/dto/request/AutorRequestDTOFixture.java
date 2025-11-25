package com.wilker.livraria_api.infrastructure.dto.request;

import com.wilker.livraria_api.infrastructure.enums.SexoEnum;
import java.time.LocalDate;

public class AutorRequestDTOFixture {

    public static AutorRequestDTO build(
                                   String nome,
                                   SexoEnum sexoEnum,
                                   String email,
                                   LocalDate dataNascimento,
                                   String paisOrigem,
                                   String cpf){

        return new AutorRequestDTO(nome, sexoEnum, email, dataNascimento, paisOrigem, cpf);


    }
}
