package com.itapp.inventorycontrol.dto.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoxDTO {
    private Long id;
    private String uuid;
    private String serialNumber;
    private String itemName;
    private int amount;
    private Date creationDate;
    private Date expirationDate;
}
