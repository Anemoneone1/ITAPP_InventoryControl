package com.itapp.inventorycontrol.mapper;

import com.itapp.inventorycontrol.dto.response.BoxWarningResponse;
import com.itapp.inventorycontrol.dto.warning.BoxWarning;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface WarningMapper {
    @Mapping(target = "boxId", source = "box.id")
    @Mapping(target = "uuid", source = "box.uuid")
    @Mapping(target = "storageId", source = "box.storage.id")
    @Mapping(target = "storageName", source = "box.storage.name")
    @Mapping(target = "itemId", source = "box.item.id")
    @Mapping(target = "itemName", source = "box.item.name")
    @Mapping(target = "expirationDate", source = "box.expirationDate")
    BoxWarningResponse boxToResponse(BoxWarning boxWarning);
}
