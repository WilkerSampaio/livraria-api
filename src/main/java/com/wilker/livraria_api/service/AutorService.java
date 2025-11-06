package com.wilker.livraria_api.service;

import com.wilker.livraria_api.infrastructure.dto.request.AutorRequestDTO;
import com.wilker.livraria_api.infrastructure.dto.response.AutorResponseDTO;
import com.wilker.livraria_api.infrastructure.entity.AutorEntity;
import com.wilker.livraria_api.infrastructure.mapper.AutorMapperConverter;
import com.wilker.livraria_api.infrastructure.mapper.AutorMapperUpdate;
import com.wilker.livraria_api.infrastructure.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorMapperConverter autorMapperConverter;
    private final AutorMapperUpdate autorMapperUpdate;
    public AutorResponseDTO RegistraAutor(AutorRequestDTO autorRequestDTO){

        AutorEntity autorEntity = new AutorEntity();

        autorEntity.setNome(autorRequestDTO.nome());
        autorEntity.setSexoEnum(autorRequestDTO.sexoEnum());
        autorEntity.setEmail(autorRequestDTO.email());
        autorEntity.setDataNascimento(autorRequestDTO.dataNascimento());
        autorEntity.setPaisOrigem(autorRequestDTO.paisOrigem());
        autorEntity.setCpf(autorRequestDTO.cpf());

        return autorMapperConverter.paraAutorResponse(autorRepository.save(autorEntity));
    }

    public AutorResponseDTO buscaAutor(Long id){
        AutorEntity autorEntity = autorRepository.findById(id).orElseThrow(()
                -> new RuntimeException("Autor não encontrado"));

        return autorMapperConverter.paraAutorResponse(autorEntity);
    }
    public AutorResponseDTO atualizaAutor(AutorRequestDTO autorRequestDTO, Long id){
        AutorEntity autorEntity = autorRepository.findById(id).orElseThrow(()
                -> new ResourceAccessException("Autor não encontrado"));

         autorMapperUpdate.updateAutor(autorRequestDTO, autorEntity);

        return autorMapperConverter.paraAutorResponse(autorRepository.save(autorEntity));
    }

}
