package com.itapp.inventorycontrol.dto.page;

import com.itapp.inventorycontrol.dto.front.BoxDTO;
import com.itapp.inventorycontrol.dto.front.StorageConditionDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WarehouseStorageDTO {
    private Long id;
    private String name;
    private String description;
    private List<StorageConditionDTO> storageConditions;
    private List<BoxDTO> boxes;
}
