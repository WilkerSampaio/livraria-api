package com.wilker.livraria_api.service;

import com.wilker.livraria_api.infrastructure.dto.request.ObraRequestDTO;
import com.wilker.livraria_api.infrastructure.dto.request.ObraRequestDTOFixture;
import com.wilker.livraria_api.infrastructure.dto.response.ObraResponseDTO;
import com.wilker.livraria_api.infrastructure.dto.response.ObraResponseDTOFixture;
import com.wilker.livraria_api.infrastructure.entity.AutorEntity;
import com.wilker.livraria_api.infrastructure.entity.ObraEntity;
import com.wilker.livraria_api.infrastructure.enums.SexoEnum;
import com.wilker.livraria_api.infrastructure.exception.ResourceNotFoundException;
import com.wilker.livraria_api.infrastructure.mapper.ObraMapperConverter;
import com.wilker.livraria_api.infrastructure.mapper.ObraMapperUpdate;
import com.wilker.livraria_api.infrastructure.repository.AutorRepository;
import com.wilker.livraria_api.infrastructure.repository.ObraRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ObraServiceTest {

    @Mock
    ObraRepository obraRepository;

    @Mock
    AutorRepository autorRepository;

    @Mock
    ObraMapperUpdate obraMapperUpdate;

    @Mock
    ObraMapperConverter obraMapperConverter;

    @InjectMocks
    ObraService obraService;

    AutorEntity autorEntity;
    ObraEntity obraEntity;
    ObraRequestDTO obraRequestDTO;
    ObraResponseDTO obraResponseDTO;
    LocalDate dataPublicacao;

    Set<Long> autoresIds;
    Set<AutorEntity> autorEntities;

    @BeforeEach
    void setup() {

        autoresIds = Set.of(1L);

        dataPublicacao = LocalDate.of(2000, 2, 1);

        autorEntity = AutorEntity.builder()
                .id(1L)
                .nome("Autor Teste")
                .sexoEnum(SexoEnum.MASCULINO)
                .email("autorteste@gmail.com")
                .dataNascimento(LocalDate.of(2000, 12, 10))
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

        obraResponseDTO = ObraResponseDTOFixture.build(
                1L,
                "Obra teste",
                "Obra teste para mapper",
                dataPublicacao,
                autoresIds
        );
    }

    @Test
    void deveRegistrarObraComSucesso() {

        when(autorRepository.findAllById(autoresIds)).thenReturn(List.of(autorEntity));

        when(obraRepository.save(any(ObraEntity.class))).thenReturn(obraEntity);

        when(obraMapperConverter.paraObraResponseDTO(obraEntity)).thenReturn(obraResponseDTO);

        ObraResponseDTO resposta = obraService.registraObra(obraRequestDTO);

        assertEquals(obraResponseDTO, resposta);

        verify(autorRepository).findAllById(autoresIds);
        verify(obraRepository).save(any(ObraEntity.class));
        verify(obraMapperConverter).paraObraResponseDTO(obraEntity);
    }

    @Test
    void deveLancarExcecaoAoRegistrarObraComAutorInexistente() {

        // Nenhum autor encontrado
        when(autorRepository.findAllById(autoresIds))
                .thenReturn(List.of());

        assertThrows(ResourceNotFoundException.class,
                () -> obraService.registraObra(obraRequestDTO));

        verify(autorRepository).findAllById(autoresIds);
        verifyNoMoreInteractions(obraRepository, obraMapperConverter);
    }

    @Test
    void deveBuscarObraComSucesso() {

        when(obraRepository.findById(1L)).thenReturn(Optional.of(obraEntity));
        when(obraMapperConverter.paraObraResponseDTO(obraEntity)).thenReturn(obraResponseDTO);

        ObraResponseDTO resposta = obraService.buscaObra(1L);

        assertEquals(obraResponseDTO, resposta);

        verify(obraRepository).findById(1L);
        verify(obraMapperConverter).paraObraResponseDTO(obraEntity);
    }

    @Test
    void deveLancarExcecaoAoBuscarObraInexistente() {

        when(obraRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> obraService.buscaObra(99L));

        verify(obraRepository).findById(99L);
    }

    @Test
    void deveAtualizarObraComSucesso() {

        when(obraRepository.findById(1L)).thenReturn(Optional.of(obraEntity));

        when(autorRepository.findAllById(autoresIds))
                .thenReturn(List.of(autorEntity));

        when(obraRepository.save(obraEntity)).thenReturn(obraEntity);

        when(obraMapperConverter.paraObraResponseDTO(obraEntity))
                .thenReturn(obraResponseDTO);

        obraService.atualizaObra(obraRequestDTO, 1L);

        verify(obraRepository).findById(1L);
        verify(obraMapperUpdate).updateObra(obraRequestDTO, obraEntity);
        verify(autorRepository).findAllById(autoresIds);
        verify(obraRepository).save(obraEntity);
    }

    @Test
    void deveLancarExcecaoAoAtualizarObraInexistente() {

        when(obraRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> obraService.atualizaObra(obraRequestDTO, 99L));

        verify(obraRepository).findById(99L);
    }

    @Test
    void deveLancarExcecaoAoAtualizarObraComAutorInexistente() {

        when(obraRepository.findById(1L)).thenReturn(Optional.of(obraEntity));

        when(autorRepository.findAllById(autoresIds))
                .thenReturn(List.of()); // Nenhum autor encontrado

        assertThrows(ResourceNotFoundException.class,
                () -> obraService.atualizaObra(obraRequestDTO, 1L));

        verify(autorRepository).findAllById(autoresIds);
    }

    @Test
    void deveDeletarObraComSucesso() {

        doNothing().when(obraRepository).deleteById(1L);

        obraService.deletaObra(1L);

        verify(obraRepository).deleteById(1L);
    }
}
