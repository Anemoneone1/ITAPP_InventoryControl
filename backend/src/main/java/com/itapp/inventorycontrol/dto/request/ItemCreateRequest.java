package com.itapp.inventorycontrol.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemCreateRequest {
    private Integer lifetime;
    private String name;
    private String description;
    private List<Long> complianceIds;
    private List<Long> storageConditionIds;
}
