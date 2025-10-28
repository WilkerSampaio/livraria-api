package com.wilker.livraria_api.infrastructure.mapper;

import com.wilker.livraria_api.infrastructure.dto.request.AutorRequestDTO;
import com.wilker.livraria_api.infrastructure.dto.response.AutorResponseDTO;
import com.wilker.livraria_api.infrastructure.entity.AutorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AutorMapperConverter {

    @Mapping(target = "id", ignore = true)
    AutorEntity paraAutorEntity(AutorRequestDTO autorRequestDTO);
    AutorResponseDTO paraAutorResponse(AutorEntity autorEntity);

}
