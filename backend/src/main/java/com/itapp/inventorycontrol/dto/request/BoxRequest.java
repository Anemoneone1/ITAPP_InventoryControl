package com.itapp.inventorycontrol.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoxRequest {
    private Long storageId;
    private String serialNumber;
    private Integer itemNum;
    private Long itemId;
    private LocalDate creationDate;
}
