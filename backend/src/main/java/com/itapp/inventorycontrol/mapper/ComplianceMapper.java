package com.itapp.inventorycontrol.mapper;

import com.itapp.inventorycontrol.dto.request.ComplianceCreateRequest;
import com.itapp.inventorycontrol.dto.response.ComplianceResponse;
import com.itapp.inventorycontrol.entity.Compliance;
import org.mapstruct.Mapper;

@Mapper
public interface ComplianceMapper {
    Compliance requestToCompliance(ComplianceCreateRequest request);

    ComplianceResponse complianceToResponse(Compliance compliance);
}
