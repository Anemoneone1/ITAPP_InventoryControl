package com.itapp.inventorycontrol.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WarehouseResponse {
    private Long id;
    private String name;
    private Double squareSpace;
    private Double freeSpace;
    private String address;
}
