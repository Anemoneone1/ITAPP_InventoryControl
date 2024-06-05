package com.itapp.inventorycontrol.dto.page;

import com.itapp.inventorycontrol.dto.creation.CreateComplianceDTO;
import com.itapp.inventorycontrol.dto.front.StorageConditionDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemCreationPageDTO {
    private List<CreateComplianceDTO> complianceList;
    private List<StorageConditionDTO> storageConditionList;
}
