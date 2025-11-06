package com.wilker.livraria_api.infrastructure.mapper;

import com.wilker.livraria_api.infrastructure.dto.request.ObraRequestDTO;
import com.wilker.livraria_api.infrastructure.dto.response.ObraResponseDTO;
import com.wilker.livraria_api.infrastructure.entity.ObraEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ObraMapperConverter {

    @Mapping(target = "id", ignore = true )
    ObraEntity paraObraEntity(ObraRequestDTO obraRequestDTO);
    ObraResponseDTO paraObraResponseDTO (ObraEntity obraEntity);

}
