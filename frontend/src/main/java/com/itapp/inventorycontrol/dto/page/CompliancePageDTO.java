package com.itapp.inventorycontrol.dto.page;

import com.itapp.inventorycontrol.dto.front.AgreementDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CompliancePageDTO {

    private Long id;
    private String name;
    private String description;
    private List<AgreementDTO> agreements;
}
