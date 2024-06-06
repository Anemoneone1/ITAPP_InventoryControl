package com.itapp.inventorycontrol.dto.warning;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ComplianceWarning {
    private Long complianceId;
    private String reason;
}
