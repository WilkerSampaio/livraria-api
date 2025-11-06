package com.wilker.livraria_api.controller;

import com.wilker.livraria_api.infrastructure.dto.request.AutorRequestDTO;
import com.wilker.livraria_api.infrastructure.dto.response.AutorResponseDTO;
import com.wilker.livraria_api.service.AutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/autor")
@RequiredArgsConstructor

public class AutorController {

    private final AutorService autorService;

    @PostMapping
    public ResponseEntity<AutorResponseDTO> registraAutor(@RequestBody AutorRequestDTO autorRequestDTO){
        return ResponseEntity.ok(autorService.criaAutor(autorRequestDTO));
    }

    @GetMapping
    public ResponseEntity<AutorResponseDTO> buscaAutor(@RequestParam ("id") Long id){
        return ResponseEntity.ok(autorService.buscaDadosAutor(id));
    }

}
