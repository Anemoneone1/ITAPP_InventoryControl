package com.itapp.inventorycontrol.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ComplianceAgreementCreateRequest {
    private Long complianceId;
    private LocalDate start;
    private LocalDate end;
}
