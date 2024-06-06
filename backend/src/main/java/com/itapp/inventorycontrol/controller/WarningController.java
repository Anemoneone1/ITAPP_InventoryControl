package com.itapp.inventorycontrol.controller;

import com.itapp.inventorycontrol.dto.response.BoxWarningResponse;
import com.itapp.inventorycontrol.dto.warning.BoxWarning;
import com.itapp.inventorycontrol.entity.Warehouse;
import com.itapp.inventorycontrol.mapper.WarningMapper;
import com.itapp.inventorycontrol.service.WarehouseService;
import com.itapp.inventorycontrol.service.WarningsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(APIVersion.current + "/warning")
public class WarningController {
    private final WarehouseService warehouseService;
    private final WarningsService warningsService;
    private final WarningMapper warningMapper;

    @GetMapping("/boxes")
    public ResponseEntity<List<BoxWarningResponse>> getBoxWarnings() {
        List<Warehouse> warehouses = warehouseService.getAll();
        List<BoxWarning> boxWarnings = new LinkedList<>();
        for (Warehouse warehouse : warehouses) {
            boxWarnings.addAll(warningsService.getWarningsForWarehouse(warehouse.getId()));
        }

        return new ResponseEntity<>(
                boxWarnings.stream().map(warningMapper::boxToResponse).toList(),
                HttpStatus.OK);
    }
}
