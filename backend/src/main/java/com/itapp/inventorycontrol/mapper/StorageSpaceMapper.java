package com.itapp.inventorycontrol.mapper;

import com.itapp.inventorycontrol.dto.request.StorageSpaceCreateRequest;
import com.itapp.inventorycontrol.dto.response.StorageConditionResponse;
import com.itapp.inventorycontrol.dto.response.StorageSpaceResponse;
import com.itapp.inventorycontrol.entity.StorageConditionStorageSpace;
import com.itapp.inventorycontrol.entity.StorageSpace;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface StorageSpaceMapper {
    @Mapping(target = "parentStorageSpace.id", source = "parentStorageSpaceId")
    @Mapping(target = "warehouse.id", source = "warehouseId")
    StorageSpace requestToStorageSpace(StorageSpaceCreateRequest request);

    @Mapping(target = "takenSpace", expression = "java(storageSpace.getSquareSpace() - storageSpace.getFreeSpace())")
    @Mapping(target = "parentStorageSpaceId", source = "parentStorageSpace.id")
    @Mapping(target = "warehouseId", source = "warehouse.id")
    StorageSpaceResponse storageSpaceToResponse(StorageSpace storageSpace);

    @Mapping(target = "id", source = "storageCondition.id")
    @Mapping(target = "condition", source = "storageCondition.condition")
    StorageConditionResponse storageConditionStorageSpaceToStorageConditionResponse(StorageConditionStorageSpace storageConditionStorageSpace);
}
