package com.itapp.inventorycontrol.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemResponse {
    private Long id;
    private String name;
    private String description;
    private Double squareSpace;
    private Double weight;
    private Integer lifetime;
    private List<ComplianceResponse> compliances;
    private List<StorageConditionResponse> storageConditions;
}
