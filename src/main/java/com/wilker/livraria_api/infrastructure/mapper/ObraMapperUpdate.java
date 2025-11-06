package com.wilker.livraria_api.infrastructure.mapper;

import com.wilker.livraria_api.infrastructure.dto.request.ObraRequestDTO;
import com.wilker.livraria_api.infrastructure.entity.ObraEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface ObraMapperUpdate {

   void updateObra(ObraRequestDTO obraRequestDTO, @MappingTarget ObraEntity obraEntity);
}
