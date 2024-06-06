package com.itapp.inventorycontrol.mapper;

import com.itapp.inventorycontrol.dto.request.WarehouseCreateRequest;
import com.itapp.inventorycontrol.dto.request.WarehouseEditRequest;
import com.itapp.inventorycontrol.dto.response.StorageListResponse;
import com.itapp.inventorycontrol.dto.response.WarehouseListResponse;
import com.itapp.inventorycontrol.dto.response.WarehouseResponse;
import com.itapp.inventorycontrol.dto.response.WarehouseWithWarningResponse;
import com.itapp.inventorycontrol.entity.Storage;
import com.itapp.inventorycontrol.entity.Warehouse;
import org.mapstruct.Mapper;

@Mapper
public interface WarehouseMapper {
    Warehouse requestToWarehouse(WarehouseCreateRequest request);

    Warehouse requestToWarehouse(WarehouseEditRequest request);

    WarehouseResponse warehouseToResponse(Warehouse warehouse);

    WarehouseListResponse warehouseToListResponse(Warehouse warehouse);

    StorageListResponse storageToStorageListResponse(Storage storage);

    WarehouseWithWarningResponse warehouseToResponseWithWarnings(Warehouse warehouse, Integer warnings);
}
