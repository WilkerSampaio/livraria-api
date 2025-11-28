package com.wilker.livraria_api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wilker.livraria_api.infrastructure.dto.request.AutorRequestDTO;
import com.wilker.livraria_api.infrastructure.dto.request.AutorRequestDTOFixture;
import com.wilker.livraria_api.infrastructure.dto.response.AutorResponseDTO;
import com.wilker.livraria_api.infrastructure.dto.response.AutorResponseDTOFixture;
import com.wilker.livraria_api.infrastructure.enums.SexoEnum;
import com.wilker.livraria_api.infrastructure.exception.GlobalExceptionHandler;
import com.wilker.livraria_api.infrastructure.exception.ResourceNotFoundException;
import com.wilker.livraria_api.service.AutorService;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AutorControllerTest {

    @Mock
    AutorService autorService;

    @InjectMocks
    AutorController autorController;

    AutorRequestDTO autorRequestDTO;

    AutorResponseDTO autorResponseDTO;

    LocalDate dataNascimento;

    private MockMvc mockMvc;

    private final String url = "/autor";
    private String json;
    private final Long AUTOR_ID = 1L;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);


    @BeforeEach
    void setup() throws JsonProcessingException {

        mockMvc = MockMvcBuilders.standaloneSetup(autorController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .alwaysDo(print())
                .build();

        dataNascimento = LocalDate.of(2000, 12, 10);

        autorRequestDTO = AutorRequestDTOFixture.build(
                "Autor Teste",
                SexoEnum.MASCULINO,
                "autorteste@gmail.com",
                dataNascimento,
                "Brasil",
                "091023123");

        autorResponseDTO = AutorResponseDTOFixture.build(
                AUTOR_ID,
                "Autor Teste",
                SexoEnum.MASCULINO,
                "autorteste@gmail.com",
                dataNascimento,
                "Brasil",
                "091023123");

        json = objectMapper.writeValueAsString(autorRequestDTO);
    }


    @Test
    void registrarAutor_ComDadosValidos_DeveRetornar200Ok() throws Exception {
        when(autorService.registraAutor(autorRequestDTO)).thenReturn(autorResponseDTO);

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(AUTOR_ID));

        verify(autorService).registraAutor(autorRequestDTO);
        verifyNoMoreInteractions(autorService);
    }

    @Test
    void buscarDadosAutor_ComIdExistente_DeveRetornar200Ok() throws Exception {
        when(autorService.buscaAutor(AUTOR_ID)).thenReturn(autorResponseDTO);

        mockMvc.perform(get(url)
                        .param("id", AUTOR_ID.toString())
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(AUTOR_ID));

        verify(autorService).buscaAutor(AUTOR_ID);
        verifyNoMoreInteractions(autorService);
    }

    @Test
    void buscarDadosAutor_ComIdInexistente_DeveRetornar404OuStatusDeErro() throws Exception {
        when(autorService.buscaAutor(AUTOR_ID)).thenThrow(new ResourceNotFoundException("Autor não encontrado"));

        mockMvc.perform(get(url)
                .param("id", AUTOR_ID.toString())
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());

        verify(autorService).buscaAutor(AUTOR_ID);
        verifyNoMoreInteractions(autorService);
    }


    @Test
    void atualizarDadosAutor_ComDadosValidosEIdExistente_DeveRetornar200Ok() throws Exception {
        when(autorService.atualizaAutor(autorRequestDTO, AUTOR_ID)).thenReturn(autorResponseDTO);

        mockMvc.perform(put(url)
                        .param("id", AUTOR_ID.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(AUTOR_ID));

        verify(autorService).atualizaAutor(autorRequestDTO, AUTOR_ID);
        verifyNoMoreInteractions(autorService);
    }

    @Test
    void deletarAutor_ComIdExistente_DeveRetornar200Ok() throws Exception {
        doNothing().when(autorService).deletaAutor(AUTOR_ID);

        mockMvc.perform(delete(url + "/{id}", AUTOR_ID)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

        verify(autorService).deletaAutor(AUTOR_ID);
        verifyNoMoreInteractions(autorService);
    }
}