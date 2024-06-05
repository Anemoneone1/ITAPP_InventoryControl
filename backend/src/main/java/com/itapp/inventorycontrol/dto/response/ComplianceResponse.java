package com.itapp.inventorycontrol.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ComplianceResponse {
    private Long id;
    private String name;
    private String description;
}
