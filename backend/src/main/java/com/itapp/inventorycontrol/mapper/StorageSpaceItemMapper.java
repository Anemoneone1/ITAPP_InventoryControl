package com.itapp.inventorycontrol.mapper;

import com.itapp.inventorycontrol.dto.request.StorageSpaceItemCreateRequest;
import com.itapp.inventorycontrol.dto.response.StorageSpaceItemResponse;
import com.itapp.inventorycontrol.entity.StorageSpaceItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Mapper
public interface StorageSpaceItemMapper {
    @Mapping(target = "storageSpace.id", source = "storageSpaceId")
    @Mapping(target = "item.id", source = "itemId")
    @Mapping(target = "creationDate", expression = "java(parseDate(request.getCreationDate()))")
    StorageSpaceItem requestToStorageSpaceItem(StorageSpaceItemCreateRequest request);

    @Mapping(target = "itemName", source = "item.name")
    @Mapping(target = "weight", source = "item.weight")
    @Mapping(target = "storageSpaceName", source = "storageSpace.name")
    @Mapping(target = "warehouseName", source = "storageSpace.warehouse.name")
    StorageSpaceItemResponse storageSpaceItemToResponse(StorageSpaceItem storageSpaceItem);

    default Date parseDate(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }
}
