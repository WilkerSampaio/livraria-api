package com.wilker.livraria_api.service;

import com.wilker.livraria_api.infrastructure.dto.request.AutorRequestDTO;
import com.wilker.livraria_api.infrastructure.dto.request.AutorRequestDTOFixture;
import com.wilker.livraria_api.infrastructure.dto.response.AutorResponseDTO;
import com.wilker.livraria_api.infrastructure.dto.response.AutorResponseDTOFixture;
import com.wilker.livraria_api.infrastructure.entity.AutorEntity;
import com.wilker.livraria_api.infrastructure.enums.SexoEnum;
import com.wilker.livraria_api.infrastructure.mapper.AutorMapperConverter;
import com.wilker.livraria_api.infrastructure.mapper.AutorMapperUpdate;
import com.wilker.livraria_api.infrastructure.repository.AutorRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AutorServiceTest {

    @Mock
    AutorRepository autorRepository;

    @Mock
    AutorMapperConverter autorMapperConverter;

    @Mock
    AutorMapperUpdate autorMapperUpdate;

    @InjectMocks
    AutorService autorService;

    AutorEntity autorEntity;
    AutorRequestDTO autorRequestDTO;
    AutorResponseDTO autorResponseDTO;
    LocalDate dataNascimento;

    @BeforeEach
    void setup() {
        dataNascimento = LocalDate.of(2000, 12, 10);

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
    void deveRegistrarAutorComSucesso() {
        when(autorMapperConverter.paraAutorEntity(autorRequestDTO)).thenReturn(autorEntity);
        when(autorRepository.save(any(AutorEntity.class))).thenReturn(autorEntity);
        when(autorMapperConverter.paraAutorResponse(autorEntity)).thenReturn(autorResponseDTO);

        AutorResponseDTO responseDTO = autorService.registraAutor(autorRequestDTO);

        assertEquals(autorResponseDTO, responseDTO);

        verify(autorMapperConverter).paraAutorEntity(autorRequestDTO);
        verify(autorRepository).save(autorEntity);
        verify(autorMapperConverter).paraAutorResponse(autorEntity);
        verifyNoMoreInteractions(autorMapperConverter, autorRepository);
    }

    @Test
    void deveBuscarAutorComSucesso() {
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autorEntity));
        when(autorMapperConverter.paraAutorResponse(autorEntity)).thenReturn(autorResponseDTO);

        AutorResponseDTO resultado = autorService.buscaAutor(1L);

        assertEquals(autorResponseDTO, resultado);

        verify(autorRepository).findById(1L);
        verify(autorMapperConverter).paraAutorResponse(autorEntity);
        verifyNoMoreInteractions(autorRepository, autorMapperConverter);
    }

    @Test
    void deveLancarExcecaoAoBuscarAutorInexistente() {
        when(autorRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> autorService.buscaAutor(99L));

        assertEquals("Autor não encontrado", ex.getMessage());

        verify(autorRepository).findById(99L);
    }

    @Test
    void deveAtualizarAutorComSucesso() {
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autorEntity));
        when(autorMapperUpdate.updateAutor(autorRequestDTO, autorEntity)).thenReturn(autorEntity);
        when(autorRepository.save(autorEntity)).thenReturn(autorEntity);
        when(autorMapperConverter.paraAutorResponse(autorEntity)).thenReturn(autorResponseDTO);

        AutorResponseDTO resposta = autorService.atualizaAutor(autorRequestDTO, 1L);

        assertEquals(autorResponseDTO, resposta);

        verify(autorRepository).findById(1L);
        verify(autorMapperUpdate).updateAutor(autorRequestDTO, autorEntity);
        verify(autorRepository).save(autorEntity);
        verify(autorMapperConverter).paraAutorResponse(autorEntity);
    }

    @Test
    void deveLancarExcecaoAoAtualizarAutorInexistente() {
        when(autorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                autorService.atualizaAutor(autorRequestDTO, 99L)
        );

        verify(autorRepository).findById(99L);
    }

    @Test
    void deveDeletarAutorComSucesso() {
        doNothing().when(autorRepository).deleteById(1L);

        autorService.deletaAutor(1L);

        verify(autorRepository).deleteById(1L);
        verifyNoMoreInteractions(autorRepository);
    }

    @Test
    void deveChamarDeleteMesmoSeAutorNaoExistir() {
        doNothing().when(autorRepository).deleteById(50L);

        autorService.deletaAutor(50L);

        verify(autorRepository).deleteById(50L);
    }
}
