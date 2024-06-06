package com.itapp.inventorycontrol.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoxResponse {
    private Long id;
    private String uuid;
    private Long storageId;
    private String serialNumber;
    private Long itemId;
    private Integer amount;
    private Date creationDate;
    private Date expirationDate;
}
