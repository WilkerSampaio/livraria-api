package com.wilker.livraria_api.infrastructure.mapper;

import com.wilker.livraria_api.infrastructure.dto.request.AutorRequestDTO;
import com.wilker.livraria_api.infrastructure.entity.AutorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AutorMapperUpdate {

    void updateAutor(AutorRequestDTO autorRequestDTO, @MappingTarget AutorEntity autorEntity);
}
