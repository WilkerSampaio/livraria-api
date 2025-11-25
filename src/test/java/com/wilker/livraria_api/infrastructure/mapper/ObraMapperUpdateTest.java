package com.wilker.livraria_api.infrastructure.mapper;

import com.wilker.livraria_api.infrastructure.dto.request.ObraRequestDTO;
import com.wilker.livraria_api.infrastructure.dto.request.ObraRequestDTOFixture;
import com.wilker.livraria_api.infrastructure.dto.response.ObraResponseDTO;
import com.wilker.livraria_api.infrastructure.entity.AutorEntity;
import com.wilker.livraria_api.infrastructure.entity.ObraEntity;
import com.wilker.livraria_api.infrastructure.enums.SexoEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(MockitoExtension.class)
public class ObraMapperUpdateTest {

    ObraMapperUpdate obraMapperUpdate;

    AutorEntity autorEntity;
    ObraEntity obraEntity;
    ObraRequestDTO obraRequestDTO;
    ObraResponseDTO obraResponseDTO;
    LocalDate dataPublicacao;
    LocalDate dataNascimento;
    Set<AutorEntity> autorEntities;
    Set<Long> autoresIds;

    @BeforeEach
    void setup(){
        autoresIds = Set.of(1L);

        obraMapperUpdate = Mappers.getMapper(ObraMapperUpdate.class);

        dataPublicacao = LocalDate.of(2000, 2, 1);
        dataNascimento = LocalDate.of(2000,12,10);

        autorEntity = AutorEntity.builder()
                .id(1L)
                .nome("Autor Teste")
                .sexoEnum(SexoEnum.MASCULINO)
                .email("autorteste@gmail.com")
                .dataNascimento(dataNascimento)
                .paisOrigem("Brasil")
                .cpf("091023123")
                .build();

        autorEntities = Set.of(autorEntity);

        obraEntity = ObraEntity.builder()
                .id(1L)
                .nome("Obra teste")
                .descricao("Obra teste para mapper")
                .dataPublicacao(dataPublicacao)
                .autores(autorEntities)
                .build();

        obraRequestDTO = ObraRequestDTOFixture.build(
                null,
                "Update na obra",
                null,
                null
        );
    }

    @Test
    void deveAtualizarObraComSucesso(){
        ObraEntity entity = obraMapperUpdate.updateObra(obraRequestDTO, obraEntity);

        assertSame(obraEntity, entity);

        assertEquals("Obra teste", entity.getNome());
        assertEquals("Update na obra", entity.getDescricao());
        assertEquals(dataPublicacao, entity.getDataPublicacao());
        assertEquals(autorEntities, entity.getAutores());
    }
}
