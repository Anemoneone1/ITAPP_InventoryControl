package com.itapp.inventorycontrol.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StorageSpaceItemCreateRequest {
    private Long storageSpaceId;
    private Long itemId;
    private String creationDate;
    private String batchNumber;
}
