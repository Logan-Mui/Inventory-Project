package com.inventory.lbm.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import com.inventory.lbm.dto.VendorDTO;
import com.inventory.lbm.entity.VendorEntity;

@Mapper(componentModel = "spring")
public interface VendorMapper {
    VendorDTO toDTO(VendorEntity entity);
    List<VendorDTO> toDTOList(List<VendorEntity> entities);

    default Page<VendorDTO> toDTOPage(Page<VendorEntity> entities) {
        return entities.map(this::toDTO);
    }
}
