package com.itapp.inventorycontrol.dto.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StorageSpaceDTO {
    private Long id;
    private String name;
    private String description;
    private int squareSpace;
    private int takenSpace;
    private int weight;
    private boolean stationary;
    private String parentStorageSpaceId;
    private String warehouseId;
    private List<StorageConditionDTO> storageConditions;

}

