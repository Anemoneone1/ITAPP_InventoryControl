package com.itapp.inventorycontrol.dto.creation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateAgreementDTO {
    private LocalDate start;
    private LocalDate end;
    private Long complianceId;
}
