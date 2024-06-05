package com.itapp.inventorycontrol.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ComplianceHistoryResponse {
    private Long id;
    private String name;
    private String description;
    private List<ComplianceAgreementHistoryResponse> agreements;
}
