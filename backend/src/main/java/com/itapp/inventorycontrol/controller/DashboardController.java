package com.itapp.inventorycontrol.controller;

import com.itapp.inventorycontrol.dto.response.DashboardResponse;
import com.itapp.inventorycontrol.entity.Company;
import com.itapp.inventorycontrol.entity.User;
import com.itapp.inventorycontrol.entity.Warehouse;
import com.itapp.inventorycontrol.mapper.WarehouseMapper;
import com.itapp.inventorycontrol.security.SignedInUsernameGetter;
import com.itapp.inventorycontrol.service.CompanyService;
import com.itapp.inventorycontrol.service.WarehouseService;
import com.itapp.inventorycontrol.service.WarningsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping(APIVersion.current + "/dashboard")
public class DashboardController {
    private final SignedInUsernameGetter signedInUsernameGetter;
    private final WarehouseService warehouseService;
    private final CompanyService companyService;
    private final WarningsService warningsService;
    private final WarehouseMapper warehouseMapper;

    @GetMapping
    public ResponseEntity<DashboardResponse> getDashboard() {
        DashboardResponse dashboard = new DashboardResponse();

        User signedUser = signedInUsernameGetter.getUser();
        dashboard.setUserName(signedUser.getName());
        dashboard.setUserSurname(signedUser.getSurname());

        Company company = companyService.getByIdOrThrow(signedUser.getCompany().getId());
        dashboard.setCompanyName(company.getName());
        dashboard.setRole(signedUser.getRole());

        List<Warehouse> warehouses = warehouseService.getAll();
        Map<Long, Integer> warehouseWarnings = warningsService.getWarningsForWarehouses(warehouses);
        dashboard.setWarehouses(warehouses.stream().map(
                w -> warehouseMapper.warehouseToResponseWithWarnings(w, warehouseWarnings.get(w.getId()))).toList());

        Integer totalWarnings = warningsService.getComplianceWarnings().size();
        for (Integer warnings : warehouseWarnings.values()) {
            totalWarnings += warnings;
        }
        dashboard.setTotalWarnings(totalWarnings);

        return new ResponseEntity<>(dashboard, HttpStatus.OK);
    }
}
