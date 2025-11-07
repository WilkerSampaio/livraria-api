package com.wilker.livraria_api.service;

import com.wilker.livraria_api.infrastructure.dto.request.ObraRequestDTO;
import com.wilker.livraria_api.infrastructure.dto.response.ObraResponseDTO;
import com.wilker.livraria_api.infrastructure.entity.AutorEntity;
import com.wilker.livraria_api.infrastructure.entity.ObraEntity;
import com.wilker.livraria_api.infrastructure.mapper.ObraMapperConverter;
import com.wilker.livraria_api.infrastructure.mapper.ObraMapperUpdate;
import com.wilker.livraria_api.infrastructure.repository.AutorRepository;
import com.wilker.livraria_api.infrastructure.repository.ObraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.Arrays.stream;

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

        Set<Long> autoresIds = obraRequestDTO.autoresIds();

        List<AutorEntity> autoresEcontrados = autorRepository.findAllById(autoresIds);

        Set<AutorEntity> autores = new HashSet<>(autoresEcontrados);

        obraEntity.setAutores(autores);

        return obraMapperConverter.paraObraResponseDTO(obraRepository.save(obraEntity));
    }


}
