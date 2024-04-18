package com.itapp.inventorycontrol.controller;

import com.itapp.inventorycontrol.dto.response.DashboardResponse;
import com.itapp.inventorycontrol.entity.Company;
import com.itapp.inventorycontrol.entity.User;
import com.itapp.inventorycontrol.mapper.WarehouseMapper;
import com.itapp.inventorycontrol.security.SignedInUsernameGetter;
import com.itapp.inventorycontrol.service.CompanyService;
import com.itapp.inventorycontrol.service.WarehouseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping(APIVersion.current + "/dashboard")
public class DashboardController {
    private final WarehouseService warehouseService;
    private final CompanyService companyService;
    private final SignedInUsernameGetter signedInUsernameGetter;
    private final WarehouseMapper warehouseMapper;

    @GetMapping
    public ResponseEntity<DashboardResponse> getDashboard() {
        DashboardResponse dashboard = new DashboardResponse();

        User signedUser = signedInUsernameGetter.getUser();
        dashboard.setUserName(signedUser.getName());
        dashboard.setUserSurname(signedUser.getSurname());

        Company company = companyService.getByIdOrThrow(signedUser.getCompany().getId());
        dashboard.setCompanyName(company.getName());

        dashboard.setWarehouses(warehouseService.getAll().stream()
                .map(warehouseMapper::warehouseToResponse)
                .collect(Collectors.toList()));

        return new ResponseEntity<>(dashboard, HttpStatus.OK);
    }
}
