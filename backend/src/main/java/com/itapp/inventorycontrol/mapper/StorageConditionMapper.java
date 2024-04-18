package com.itapp.inventorycontrol.mapper;

import com.itapp.inventorycontrol.dto.request.StorageConditionCreateRequest;
import com.itapp.inventorycontrol.dto.response.StorageConditionResponse;
import com.itapp.inventorycontrol.entity.StorageCondition;
import org.mapstruct.Mapper;

@Mapper
public interface StorageConditionMapper {
    StorageCondition requestToStorageCondition(StorageConditionCreateRequest request);

    StorageConditionResponse StorageConditionToResponse(StorageCondition storageCondition);
}
