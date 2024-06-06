package com.itapp.inventorycontrol.dto.response;

import com.itapp.inventorycontrol.entity.UserRole;
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
    private UserRole role;
    private Integer totalWarnings;
    private List<WarehouseWithWarningResponse> warehouses;
}
