package com.itapp.inventorycontrol.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoxFullResponse {
    private Long id;
    private String uuid;
    private String serialNumber;
    private String itemName;
    private Integer amount;
    private Date creationDate;
    private Date expirationDate;
}
