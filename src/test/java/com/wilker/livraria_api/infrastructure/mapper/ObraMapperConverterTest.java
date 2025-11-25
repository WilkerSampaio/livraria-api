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

@ExtendWith(MockitoExtension.class)
public class ObraMapperConverterTest {
    ObraMapperConverter obraMapperConverter;

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

        obraMapperConverter = Mappers.getMapper(ObraMapperConverter.class);

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
                "Obra teste",
                "Obra teste para mapper",
                dataPublicacao,
                autoresIds
                );

    }

    @Test
    void deveConverterParaEntityComSucesso(){
        ObraEntity entity = obraMapperConverter.paraObraEntity(obraRequestDTO);

        assertEquals("Obra teste", entity.getNome());
        assertEquals("Obra teste para mapper", entity.getDescricao());
        assertEquals(dataPublicacao, entity.getDataPublicacao());
        assertEquals(null, entity.getAutores());

    }

    @Test
    void deveConverterParaResponseComSucesso(){

        ObraResponseDTO responseDTO = obraMapperConverter.paraObraResponseDTO(obraEntity);

        assertEquals(1L, responseDTO.id());
        assertEquals("Obra teste", responseDTO.nome());
        assertEquals("Obra teste para mapper", responseDTO.descricao());
        assertEquals(dataPublicacao, responseDTO.dataPublicacao());
        assertEquals(Set.of(1L), responseDTO.autoresIds());


    }
}
