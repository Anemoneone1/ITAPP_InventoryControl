package com.itapp.inventorycontrol.controller;

import com.itapp.inventorycontrol.dto.request.WarehouseCreateRequest;
import com.itapp.inventorycontrol.dto.request.WarehouseDeleteRequest;
import com.itapp.inventorycontrol.dto.request.WarehouseEditRequest;
import com.itapp.inventorycontrol.dto.response.WarehouseResponse;
import com.itapp.inventorycontrol.entity.Warehouse;
import com.itapp.inventorycontrol.mapper.WarehouseMapper;
import com.itapp.inventorycontrol.service.WarehouseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping(APIVersion.current + "/warehouse")
public class WarehouseController {
    private final WarehouseService warehouseService;
    private final WarehouseMapper warehouseMapper;

    @GetMapping
    public ResponseEntity<List<WarehouseResponse>> getAll() {
        return new ResponseEntity<>(warehouseService.getAll().stream()
                .map(warehouseMapper::warehouseToResponse)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<WarehouseResponse> create(@RequestBody WarehouseCreateRequest request) {
        Warehouse warehouse = warehouseService.create(warehouseMapper.requestToWarehouse(request));

        return new ResponseEntity<>(
                warehouseMapper.warehouseToResponse(warehouse),
                HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<WarehouseResponse> edit(@RequestBody WarehouseEditRequest request) {
        Warehouse warehouse = warehouseService.edit(warehouseMapper.requestToWarehouse(request));

        return new ResponseEntity<>(
                warehouseMapper.warehouseToResponse(warehouse),
                HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody WarehouseDeleteRequest request) {
        warehouseService.delete(request.getWarehouseId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
