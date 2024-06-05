package com.itapp.inventorycontrol.mapper;

import com.itapp.inventorycontrol.dto.request.ComplianceAgreementCreateRequest;
import com.itapp.inventorycontrol.dto.response.ComplianceAgreementResponse;
import com.itapp.inventorycontrol.entity.ComplianceAgreement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ComplianceAgreementMapper {
    @Mapping(target = "compliance.id", source = "complianceId")
    ComplianceAgreement requestToComplianceAgreement(ComplianceAgreementCreateRequest request);

    @Mapping(target = "complianceId", source = "compliance.id")
    ComplianceAgreementResponse complianceAgreementToResponse(ComplianceAgreement compliance);
}
