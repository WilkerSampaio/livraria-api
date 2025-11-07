package com.wilker.livraria_api.infrastructure.mapper;

import com.wilker.livraria_api.infrastructure.dto.request.ObraRequestDTO;
import com.wilker.livraria_api.infrastructure.dto.response.ObraResponseDTO;
import com.wilker.livraria_api.infrastructure.entity.AutorEntity;
import com.wilker.livraria_api.infrastructure.entity.ObraEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ObraMapperConverter {

    // Mantido para mapear DTO de Request para Entity
    @Mapping(target = "id", ignore = true )
    @Mapping(target = "autores", ignore = true)
    ObraEntity paraObraEntity(ObraRequestDTO obraRequestDTO);

    // Mapeamento explícito para que o MapStruct saiba que 'autores' (Entity)
    // deve preencher 'autoresIds' (ResponseDTO).
    @Mapping(target = "autoresIds", source = "autores")
    ObraResponseDTO paraObraResponseDTO (ObraEntity obraEntity);

    // Metodo auxiliar (default) que o MapStruct usará para converter
    // Set<AutorEntity> em Set<Long> (IDs)
    default Set<Long> mapAutoresToIds(Set<AutorEntity> autores) {
        if (autores == null) {
            return Collections.emptySet();
        }
        return autores.stream()
                .map(AutorEntity::getId) // Extrai o ID de cada AutorEntity
                .collect(Collectors.toSet());
    }
}