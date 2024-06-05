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
    private Integer lifetime;
    private String name;
    private String description;
    private List<ComplianceResponse> compliances;
    private List<StorageConditionResponse> storageConditions;
}
