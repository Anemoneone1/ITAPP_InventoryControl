package com.itapp.inventorycontrol.dto.creation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoxCreationDTO {
    private Long storageId;
    private String serialNumber;
    private int itemNum;
    private Long itemId;
    private LocalDate creationDate;
}
