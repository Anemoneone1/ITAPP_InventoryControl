package com.itapp.inventorycontrol.mapper;

import com.itapp.inventorycontrol.dto.response.BoxItemResponse;
import com.itapp.inventorycontrol.dto.response.BoxResponse;
import com.itapp.inventorycontrol.entity.Box;
import com.itapp.inventorycontrol.entity.BoxItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BoxMapper {
    @Mapping(target = "storageId", source = "storage.id")
    BoxResponse boxToResponse(Box box);

    @Mapping(target = "itemId", source = "item.id")
    BoxItemResponse BoxItemToResponse(BoxItem boxItem);
}
