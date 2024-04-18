package com.itapp.inventorycontrol.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StorageSpaceResponse {
    private Long id;
    private String name;
    private String description;
    private Double squareSpace;
    private Double takenSpace;
    private Double weight;
    private boolean stationary;
    private String parentStorageSpaceId;
    private String warehouseId;
    private List<StorageConditionResponse> storageConditions;
}
