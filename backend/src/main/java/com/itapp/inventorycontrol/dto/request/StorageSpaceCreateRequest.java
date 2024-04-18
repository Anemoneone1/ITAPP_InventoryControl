package com.itapp.inventorycontrol.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StorageSpaceCreateRequest {
    private String name;
    private String description;
    private Double squareSpace;
    private Double weight;
    private boolean stationary;
    private Long parentStorageSpaceId;
    private Long warehouseId;
    private List<Long> storageConditionIds;
}
