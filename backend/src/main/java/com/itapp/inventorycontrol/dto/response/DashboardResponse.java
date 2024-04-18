package com.itapp.inventorycontrol.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DashboardResponse {
    private String userName;
    private String userSurname;
    private String companyName;
    private List<WarehouseResponse> warehouses;
}
