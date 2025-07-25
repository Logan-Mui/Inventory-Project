package com.inventory.lbm.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import com.inventory.lbm.dto.RequestDTO;
import com.inventory.lbm.entity.RequestEntity;

@Mapper(componentModel = "spring")
public interface RequestMapper {

    RequestDTO toDTO(RequestEntity request);
    
    List<RequestDTO> toDTOList(List<RequestEntity> requestList);
    
    default Page<RequestDTO> toDTOPage(Page<RequestEntity> entities) {
        return entities.map(this::toDTO);
    }
}
