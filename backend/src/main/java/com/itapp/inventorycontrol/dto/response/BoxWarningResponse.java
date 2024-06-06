package com.itapp.inventorycontrol.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoxWarningResponse {
    private Long boxId;
    private String uuid;
    private Long storageId;
    private String storageName;
    private Long itemId;
    private String itemName;
    private Date expirationDate;
    private String reason;
}
