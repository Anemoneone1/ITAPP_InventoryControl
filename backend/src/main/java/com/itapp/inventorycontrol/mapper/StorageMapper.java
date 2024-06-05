package com.itapp.inventorycontrol.mapper;

import com.itapp.inventorycontrol.dto.request.StorageCreateRequest;
import com.itapp.inventorycontrol.dto.request.StorageEditRequest;
import com.itapp.inventorycontrol.dto.response.StorageConditionResponse;
import com.itapp.inventorycontrol.dto.response.StorageResponse;
import com.itapp.inventorycontrol.entity.Storage;
import com.itapp.inventorycontrol.entity.StorageConditionStorage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface StorageMapper {
    @Mapping(target = "warehouse.id", source = "warehouseId")
    Storage requestToStorageSpace(StorageCreateRequest request);

    @Mapping(target = "warehouse.id", source = "warehouseId")
    Storage requestToStorageSpace(StorageEditRequest request);

    @Mapping(target = "warehouseId", source = "warehouse.id")
    StorageResponse storageSpaceToResponse(Storage storage);

    @Mapping(target = "id", source = "storageCondition.id")
    @Mapping(target = "condition", source = "storageCondition.condition")
    StorageConditionResponse storageConditionStorageSpaceToStorageConditionResponse(StorageConditionStorage storageConditionStorage);
}
