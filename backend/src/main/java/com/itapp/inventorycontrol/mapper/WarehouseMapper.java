package com.itapp.inventorycontrol.mapper;

import com.itapp.inventorycontrol.dto.request.WarehouseCreateRequest;
import com.itapp.inventorycontrol.dto.request.WarehouseEditRequest;
import com.itapp.inventorycontrol.dto.response.WarehouseResponse;
import com.itapp.inventorycontrol.entity.Warehouse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface WarehouseMapper {
    Warehouse requestToWarehouse(WarehouseCreateRequest request);

    Warehouse requestToWarehouse(WarehouseEditRequest request);

    WarehouseResponse warehouseToResponse(Warehouse warehouse);
}
