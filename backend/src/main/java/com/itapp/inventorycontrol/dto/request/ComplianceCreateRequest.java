package com.itapp.inventorycontrol.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ComplianceCreateRequest {
    private String name;
    private String description;
}
