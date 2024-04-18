package com.itapp.inventorycontrol.dto.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StorageItemDTO {
    private Long id;
    private String storageItemName;
    private Date expirationDate;
    private Double weight;
    private String containerName;
    private String warehouseName;
}
