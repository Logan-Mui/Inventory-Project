package com.inventory.lbm.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import com.inventory.lbm.dto.InventoryDTO;
import com.inventory.lbm.entity.InventoryEntity;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    
    InventoryDTO toDTO(InventoryEntity inventory);

    List<InventoryDTO> toDTOList(List<InventoryEntity> inventoryList);
    
    default Page<InventoryDTO> toDTOPage(Page<InventoryEntity> entities) {
        return entities.map(this::toDTO);
    }
}
