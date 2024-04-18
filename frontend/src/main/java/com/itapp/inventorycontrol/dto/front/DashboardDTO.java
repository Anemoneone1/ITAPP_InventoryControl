package com.itapp.inventorycontrol.dto.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DashboardDTO {
    private String userName;
    private String userSurname;
    private String companyName;
    private List<WarehouseDTO> warehouses;
}
