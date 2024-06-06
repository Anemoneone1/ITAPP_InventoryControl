package com.itapp.inventorycontrol.dto.creation;

import com.itapp.inventorycontrol.dto.front.StorageConditionDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StorageCreationDTO {
    private Long warehouseId;
    private String name;
    private String description;
    private List<StorageConditionDTO> storageConditions;
}
