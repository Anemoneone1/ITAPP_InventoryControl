package com.itapp.inventorycontrol.dto.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateItemDTO {
    private String name;
    private double squareSpace;
    private double weight;
    private int lifetime;
    private String description;
    private List<Long> complianceIds;
    private List<Long> storageConditionIds;

}