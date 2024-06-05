package com.itapp.inventorycontrol.dto.page;

import com.itapp.inventorycontrol.dto.front.WarehouseDTO;
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
    private String role;
    private int warning;
    private List<WarehouseDTO> warehouses;
}
