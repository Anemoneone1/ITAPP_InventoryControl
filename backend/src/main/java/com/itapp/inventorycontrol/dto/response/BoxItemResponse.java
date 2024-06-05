package com.itapp.inventorycontrol.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoxItemResponse {
    private Long id;
    private Long itemId;
    private Date creationDate;
    private Date expirationDate;
}
