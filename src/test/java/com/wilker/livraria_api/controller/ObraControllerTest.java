package com.wilker.livraria_api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wilker.livraria_api.infrastructure.dto.request.ObraRequestDTO;
import com.wilker.livraria_api.infrastructure.dto.request.ObraRequestDTOFixture;
import com.wilker.livraria_api.infrastructure.dto.response.ObraResponseDTO;
import com.wilker.livraria_api.infrastructure.dto.response.ObraResponseDTOFixture;
import com.wilker.livraria_api.infrastructure.exception.GlobalExceptionHandler;
import com.wilker.livraria_api.infrastructure.exception.ResourceNotFoundException;
import com.wilker.livraria_api.service.ObraService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ObraControllerTest {

    @Mock
    ObraService obraService;

    @InjectMocks
    ObraController obraController;

    ObraRequestDTO obraRequestDTO;
    ObraResponseDTO obraResponseDTO;
    LocalDate dataNascimento;
    LocalDate dataPublicacao;
    Set<Long> autoresIds;

    private String json;
    private String url;
    private MockMvc mockMvc;

    private final Long OBRA_ID = 1L;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @BeforeEach
    void setup() throws JsonProcessingException {
        url = "/obra";
        autoresIds = Set.of(1L);

        mockMvc = MockMvcBuilders.standaloneSetup(obraController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .alwaysDo(print())
                .build();
        dataPublicacao = LocalDate.of(2000, 2, 1);
        dataNascimento = LocalDate.of(2000, 12, 10);

        obraRequestDTO = ObraRequestDTOFixture.build(
                "Obra teste",
                "Obra teste para mapper",
                dataPublicacao,
                autoresIds);

        obraResponseDTO = ObraResponseDTOFixture.build(
                OBRA_ID,
                "Obra teste",
                "Obra teste para mapper",
                dataPublicacao,
                autoresIds);

        json = objectMapper.writeValueAsString(obraRequestDTO);

    }

    @Test
    void registarDadosObra_ComDadosValidos_DeveRetornar200Ok() throws Exception {
        when(obraService.registraObra(obraRequestDTO)).thenReturn(obraResponseDTO);

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(OBRA_ID));

        verify(obraService).registraObra(obraRequestDTO);
        verifyNoMoreInteractions(obraService);
    }

    @Test
    void registarDadosObra_ComAutorInexistente_DeveRetornar404NotFound() throws Exception {
        when(obraService.registraObra(obraRequestDTO)).thenThrow(new ResourceNotFoundException("Os seguintes autores não foram encontrados: [1]"));

        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isNotFound());

        verify(obraService).registraObra(obraRequestDTO);
        verifyNoMoreInteractions(obraService);
    }

    @Test
    void buscarDadosObra_ComIdExistente_DeveRetornar200Ok() throws Exception {
        when(obraService.buscaObra(OBRA_ID)).thenReturn(obraResponseDTO);

        mockMvc.perform(get(url)
                        .param("id", OBRA_ID.toString())
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(OBRA_ID));

        verify(obraService).buscaObra(OBRA_ID);
        verifyNoMoreInteractions(obraService);
    }

    @Test
    void buscarDadosObra_ComIdInexistente_DeveRetornar404NotFound() throws Exception {
        when(obraService.buscaObra(OBRA_ID)).thenThrow(new ResourceNotFoundException("Nenhuma obra encontrada com o id: " + OBRA_ID));

        mockMvc.perform(get(url)
                .param("id", OBRA_ID.toString())
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());

        verify(obraService).buscaObra(OBRA_ID);
        verifyNoMoreInteractions(obraService);
    }

    @Test
    void atualizarDadosObra_ComDadosValidos_DeveRetornar200Ok() throws Exception {
        when(obraService.atualizaObra(obraRequestDTO, OBRA_ID)).thenReturn(obraResponseDTO);

        mockMvc.perform(put(url)
                        .param("id", OBRA_ID.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(OBRA_ID));

        verify(obraService).atualizaObra(obraRequestDTO, OBRA_ID);
        verifyNoMoreInteractions(obraService);
    }

    @Test
    void atualizarDadosObra_ComObraInexistente_DeveRetornar404NotFound() throws Exception {
        when(obraService.atualizaObra(obraRequestDTO, OBRA_ID)).thenThrow(new ResourceNotFoundException("Nenhuma obra encontrada com o id: " + OBRA_ID));

        mockMvc.perform(put(url)
                .param("id", OBRA_ID.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isNotFound());

        verify(obraService).atualizaObra(obraRequestDTO, OBRA_ID);
        verifyNoMoreInteractions(obraService);
    }

    @Test
    void atualizarDadosObra_ComAutorInexistente_DeveRetornar404NotFound() throws Exception {
        when(obraService.atualizaObra(obraRequestDTO, OBRA_ID)).thenThrow(new ResourceNotFoundException("Os seguintes autores não foram encontrados: [99]"));

        mockMvc.perform(put(url)
                .param("id", OBRA_ID.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isNotFound());

        verify(obraService).atualizaObra(obraRequestDTO, OBRA_ID);
        verifyNoMoreInteractions(obraService);
    }

    @Test
    void deletarDadosObra_ComIdExistente_DeveRetornar200Ok() throws Exception {
        doNothing().when(obraService).deletaObra(OBRA_ID);

        mockMvc.perform(delete(url + "/{id}", OBRA_ID)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        verify(obraService).deletaObra(OBRA_ID);
        verifyNoMoreInteractions(obraService);
    }
}