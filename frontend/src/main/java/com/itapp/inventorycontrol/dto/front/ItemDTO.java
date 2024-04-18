package com.itapp.inventorycontrol.dto.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class ItemDTO {
    private Long id;
    private String name;
    private String description;
    private int squareSpace;
    private int weight;
    private int lifetime;
    private List<ComplianceDTO> compliances;
    private List<StorageConditionDTO> storageConditions;
}
