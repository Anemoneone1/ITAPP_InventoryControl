package com.itapp.inventorycontrol.mapper;

import com.itapp.inventorycontrol.dto.request.StorageSpaceItemCreateRequest;
import com.itapp.inventorycontrol.dto.response.StorageSpaceItemResponse;
import com.itapp.inventorycontrol.entity.StorageSpaceItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface StorageSpaceItemMapper {
    @Mapping(target = "storageSpace.id", source = "storageSpaceId")
    @Mapping(target = "item.id", source = "itemId")
    StorageSpaceItem requestToStorageSpaceItem(StorageSpaceItemCreateRequest request);

    @Mapping(target = "itemName", source = "item.name")
    @Mapping(target = "weight", source = "item.weight")
    @Mapping(target = "storageSpaceName", source = "storageSpace.name")
    @Mapping(target = "warehouseName", source = "storageSpace.warehouse.name")
    StorageSpaceItemResponse storageSpaceItemToResponse(StorageSpaceItem storageSpaceItem);
}
