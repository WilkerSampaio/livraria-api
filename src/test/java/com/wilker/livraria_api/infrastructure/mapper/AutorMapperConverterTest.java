package com.wilker.livraria_api.infrastructure.mapper;

import com.wilker.livraria_api.infrastructure.dto.request.AutorRequestDTO;
import com.wilker.livraria_api.infrastructure.dto.request.AutorRequestDTOFixture;
import com.wilker.livraria_api.infrastructure.dto.response.AutorResponseDTO;
import com.wilker.livraria_api.infrastructure.dto.response.AutorResponseDTOFixture;
import com.wilker.livraria_api.infrastructure.entity.AutorEntity;
import com.wilker.livraria_api.infrastructure.enums.SexoEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AutorMapperConverterTest {

    AutorMapperConverter autorMapperConverter;
    AutorEntity autorEntity;
    AutorRequestDTO autorRequestDTO;
    AutorResponseDTO autorResponseDTO;
    LocalDate dataNascimento;
    LocalDate dataPublicacao;

    @BeforeEach
    void setup(){
        autorMapperConverter = Mappers.getMapper(AutorMapperConverter.class);

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
                "Autor Teste",
                SexoEnum.MASCULINO,
                "autorteste@gmail.com",
                dataNascimento,
                "Brasil",
                "091023123"
                );

        autorResponseDTO = AutorResponseDTOFixture.build(
                1L,
                "Autor Teste",
                SexoEnum.MASCULINO,
                "autorteste@gmail.com",
                dataNascimento,
                "Brasil",
                "091023123"
        );
    }

    @Test
    void deveConverterParaEntityComSucesso(){

       AutorEntity entity = autorMapperConverter.paraAutorEntity(autorRequestDTO);

       assertEquals("Autor Teste", entity.getNome());
       assertEquals(SexoEnum.MASCULINO, entity.getSexoEnum());
       assertEquals("autorteste@gmail.com", entity.getEmail());
       assertEquals(dataNascimento, entity.getDataNascimento());
       assertEquals("Brasil", entity.getPaisOrigem());
       assertEquals("091023123", entity.getCpf());

    }

    @Test
    void deveConverterParaResponseComSucesso(){

        AutorResponseDTO responseDTO = autorMapperConverter.paraAutorResponse(autorEntity);

        assertEquals(autorResponseDTO, responseDTO);
    }

}
