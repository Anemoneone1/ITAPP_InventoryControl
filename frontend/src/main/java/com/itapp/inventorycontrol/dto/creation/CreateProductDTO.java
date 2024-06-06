package com.itapp.inventorycontrol.dto.creation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateProductDTO {
    private String creationDate;
    private String serialNumber;
    private Long itemId;
    private Long storageId;
}
