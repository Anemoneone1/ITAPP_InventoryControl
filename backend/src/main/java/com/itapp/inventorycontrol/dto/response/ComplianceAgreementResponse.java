package com.itapp.inventorycontrol.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ComplianceAgreementResponse {
    private Long id;
    private Long complianceId;
    private Date start;
    private Date end;
}
