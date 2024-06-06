package com.itapp.inventorycontrol.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CompliancesAndConditionsResponse {
    private List<ComplianceResponse> compliances;
    private List<StorageConditionResponse> storageConditions;
}
