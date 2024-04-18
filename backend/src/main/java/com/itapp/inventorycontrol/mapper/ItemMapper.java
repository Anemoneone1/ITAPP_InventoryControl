package com.itapp.inventorycontrol.mapper;

import com.itapp.inventorycontrol.dto.request.ItemCreateRequest;
import com.itapp.inventorycontrol.dto.request.ItemEditRequest;
import com.itapp.inventorycontrol.dto.response.ComplianceResponse;
import com.itapp.inventorycontrol.dto.response.ItemResponse;
import com.itapp.inventorycontrol.dto.response.StorageConditionResponse;
import com.itapp.inventorycontrol.entity.Item;
import com.itapp.inventorycontrol.entity.ItemCompliance;
import com.itapp.inventorycontrol.entity.StorageConditionItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ItemMapper {
    Item requestToItem(ItemCreateRequest request);

    Item requestToItem(ItemEditRequest request);

    ItemResponse itemToResponse(Item item);

    @Mapping(target = "id", source = "compliance.id")
    @Mapping(target = "name", source = "compliance.name")
    ComplianceResponse itemComplianceToComplianceResponse(ItemCompliance itemCompliance);

    @Mapping(target = "id", source = "storageCondition.id")
    @Mapping(target = "condition", source = "storageCondition.condition")
    StorageConditionResponse storageConditionItemToStorageConditionResponse(StorageConditionItem storageConditionItem);
}
