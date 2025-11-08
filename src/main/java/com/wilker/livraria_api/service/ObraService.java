package com.wilker.livraria_api.service;

import com.wilker.livraria_api.infrastructure.dto.request.ObraRequestDTO;
import com.wilker.livraria_api.infrastructure.dto.response.ObraResponseDTO;
import com.wilker.livraria_api.infrastructure.entity.AutorEntity;
import com.wilker.livraria_api.infrastructure.entity.ObraEntity;
import com.wilker.livraria_api.infrastructure.exception.ResourceNotFoundException;
import com.wilker.livraria_api.infrastructure.mapper.ObraMapperConverter;
import com.wilker.livraria_api.infrastructure.mapper.ObraMapperUpdate;
import com.wilker.livraria_api.infrastructure.repository.AutorRepository;
import com.wilker.livraria_api.infrastructure.repository.ObraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ObraService {

    private final ObraRepository obraRepository;
    private final AutorRepository autorRepository;
    private final ObraMapperConverter obraMapperConverter;
    private final ObraMapperUpdate obraMapperUpdate;

    public ObraResponseDTO registraObra(ObraRequestDTO obraRequestDTO){

        ObraEntity obraEntity = new ObraEntity();
        obraEntity.setNome(obraRequestDTO.nome());
        obraEntity.setDescricao(obraRequestDTO.descricao());
        obraEntity.setDataPublicacao(obraRequestDTO.dataPublicacao());

        Set<Long> autoresIds  = obraRequestDTO.autoresIds();

        List<AutorEntity> autoresEncontrados = autorRepository.findAllById(autoresIds);

        if(autoresIds.size() != autoresEncontrados.size()){

            Set<Long> idsEncontrados = autoresEncontrados.stream()
                    .map(AutorEntity::getId).collect(Collectors.toSet());

            Set<Long> idsNaoEncontrados = new HashSet<>(autoresIds);
            idsNaoEncontrados.removeAll(idsEncontrados);

            throw new ResourceNotFoundException("Os seguintes autores não foram encontrados: " + idsNaoEncontrados);

        }
        obraEntity.setAutores(new HashSet<>(autoresEncontrados));
        return obraMapperConverter.paraObraResponseDTO(obraRepository.save(obraEntity));
    }


}
