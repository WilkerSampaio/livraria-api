package com.wilker.livraria_api.infrastructure.mapper;

import com.wilker.livraria_api.infrastructure.dto.request.AutorRequestDTO;
import com.wilker.livraria_api.infrastructure.dto.request.AutorRequestDTOFixture;
import com.wilker.livraria_api.infrastructure.entity.AutorEntity;
import com.wilker.livraria_api.infrastructure.enums.SexoEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(MockitoExtension.class)
public class AutorMapperUpdateTest {

    AutorMapperUpdate autorMapperUpdate;
    AutorEntity autorEntity;
    AutorRequestDTO autorRequestDTO;
    LocalDate dataNascimento;
    LocalDate dataPublicacao;

    @BeforeEach
    void setup(){
        autorMapperUpdate = Mappers.getMapper(AutorMapperUpdate.class);

        dataNascimento = LocalDate.of(2000,12,10);
        dataPublicacao = LocalDate.of(1982, 9, 10);

        autorEntity = AutorEntity.builder()
                .id(1L)
                .nome("Autor Teste")
                .sexoEnum(SexoEnum.MASCULINO)
                .email("autorteste@gmail.com")
                .dataNascimento(dataNascimento)
                .paisOrigem("Brasil")
                .cpf("091023123")
                .build();

        autorRequestDTO = AutorRequestDTOFixture.build(
                "Clarice Lispector",
                SexoEnum.FEMININO,
                null,
                null,
                null,
                "0111122233"
        );
    }

    @Test
   void deveAtualizarAutorComSucesso(){

        AutorEntity entity = autorMapperUpdate.updateAutor(autorRequestDTO, autorEntity);

        assertSame(autorEntity, entity);

        assertEquals("Clarice Lispector", entity.getNome());
        assertEquals(SexoEnum.FEMININO, entity.getSexoEnum());
        assertEquals("autorteste@gmail.com", entity.getEmail());
        assertEquals(dataNascimento, entity.getDataNascimento());
        assertEquals("Brasil", entity.getPaisOrigem());
        assertEquals("0111122233", entity.getCpf());

    }


}
