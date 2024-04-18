package com.itapp.inventorycontrol.dto.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WarehouseDTO {
    private long id;
    private String name;
    private Double squareSpace;
    private Double takenSpace;
    private String address;
}
