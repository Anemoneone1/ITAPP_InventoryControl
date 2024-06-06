package com.itapp.inventorycontrol.dto.creation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateWarehouseDTO {
    private String name;
    private String address;
}
