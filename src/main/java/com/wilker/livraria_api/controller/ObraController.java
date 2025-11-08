package com.wilker.livraria_api.controller;

import com.wilker.livraria_api.infrastructure.dto.request.ObraRequestDTO;
import com.wilker.livraria_api.infrastructure.dto.response.ObraResponseDTO;
import com.wilker.livraria_api.service.ObraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/obra")
@RequiredArgsConstructor
public class ObraController {

    private final ObraService obraService;

    @PostMapping
    public ResponseEntity<ObraResponseDTO> registarDadosObra(@Valid @RequestBody ObraRequestDTO obraRequestDTO){
        return ResponseEntity.ok(obraService.registraObra(obraRequestDTO));
    }



}
