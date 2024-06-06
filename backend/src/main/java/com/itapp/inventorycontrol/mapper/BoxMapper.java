package com.itapp.inventorycontrol.mapper;

import com.itapp.inventorycontrol.dto.response.BoxFullResponse;
import com.itapp.inventorycontrol.dto.response.BoxResponse;
import com.itapp.inventorycontrol.dto.warning.BoxWarning;
import com.itapp.inventorycontrol.entity.Box;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BoxMapper {
    @Mapping(target = "storageId", source = "storage.id")
    @Mapping(target = "itemId", source = "item.id")
    BoxResponse boxToResponse(Box box);

    @Mapping(target = "itemName", source = "item.name")
    BoxFullResponse boxToFullResponse(Box box);

    @Mapping(target = "boxId", source = "id")
    BoxWarning boxToWarning(Box box);
}
