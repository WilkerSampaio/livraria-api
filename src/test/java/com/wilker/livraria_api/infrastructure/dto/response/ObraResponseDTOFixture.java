package com.wilker.livraria_api.infrastructure.dto.response;

import java.time.LocalDate;
import java.util.Set;

public class ObraResponseDTOFixture {

    public static ObraResponseDTO build(Long id,
                                 String nome,
                                 String descricao,
                                 LocalDate dataPublicacao,
                                 Set<Long> autoresIds){
        return new ObraResponseDTO(id, nome, descricao, dataPublicacao, autoresIds);
    }
}
