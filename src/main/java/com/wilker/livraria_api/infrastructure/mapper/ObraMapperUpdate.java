package com.wilker.livraria_api.infrastructure.mapper;

import com.wilker.livraria_api.infrastructure.dto.request.ObraRequestDTO;
import com.wilker.livraria_api.infrastructure.entity.ObraEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ObraMapperUpdate {

   @Mapping(target = "id", ignore = true)
   @Mapping(target = "autores", ignore = true)
   void updateObra(ObraRequestDTO obraRequestDTO, @MappingTarget ObraEntity obraEntity);
}
