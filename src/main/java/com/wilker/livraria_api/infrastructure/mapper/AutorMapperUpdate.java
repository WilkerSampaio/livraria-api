package com.wilker.livraria_api.infrastructure.mapper;

import com.wilker.livraria_api.infrastructure.dto.request.AutorRequestDTO;
import com.wilker.livraria_api.infrastructure.entity.AutorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AutorMapperUpdate {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "obras", ignore = true)
    AutorEntity updateAutor(AutorRequestDTO autorRequestDTO, @MappingTarget AutorEntity autorEntity);
}
