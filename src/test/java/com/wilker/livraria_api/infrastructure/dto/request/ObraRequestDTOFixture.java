package com.wilker.livraria_api.infrastructure.dto.request;

import java.time.LocalDate;
import java.util.Set;

public class ObraRequestDTOFixture {

    public static ObraRequestDTO build(
                                 String nome,
                                 String descricao,
                                 LocalDate dataPublicacao,
                                 Set<Long> autoresIds){

        return  new ObraRequestDTO(nome, descricao, dataPublicacao, autoresIds);

    }
}
