package com.itapp.inventorycontrol.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StorageEditRequest {
    private Long id;
    private Long warehouseId;
    private String name;
    private String description;
    private List<Long> storageConditionIds;
}
