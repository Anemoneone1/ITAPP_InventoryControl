package com.itapp.inventorycontrol.dto.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateProductDTO {
    private String creationDate;
    private String batchNumber;
    private Long itemId;
    private Long storageSpaceId;
}
