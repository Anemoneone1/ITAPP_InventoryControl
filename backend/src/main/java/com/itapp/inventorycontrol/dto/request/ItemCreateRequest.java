package com.itapp.inventorycontrol.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemCreateRequest {
    private String name;
    private Double squareSpace;
    private Double weight;
    private Integer lifetime;
    private String description;
    private List<Long> complianceIds;
    private List<Long> storageConditionIds;
}
