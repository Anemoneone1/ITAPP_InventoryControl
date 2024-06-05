package com.itapp.inventorycontrol.dto.creation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateAgreementDTO {
    private Date start;
    private Date end;
    private Long complianceId;
}
