package com.itapp.inventorycontrol.controller;

import com.itapp.inventorycontrol.dto.request.ComplianceAgreementCreateRequest;
import com.itapp.inventorycontrol.dto.request.ComplianceAgreementDeleteRequest;
import com.itapp.inventorycontrol.dto.response.ComplianceAgreementResponse;
import com.itapp.inventorycontrol.entity.ComplianceAgreement;
import com.itapp.inventorycontrol.mapper.ComplianceAgreementMapper;
import com.itapp.inventorycontrol.service.ComplianceAgreementService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping(APIVersion.current + "/compliance-agreement")
public class ComplianceAgreementController {
    private final ComplianceAgreementService complianceAgreementService;
    private final ComplianceAgreementMapper complianceAgreementMapper;

    @GetMapping
    public ResponseEntity<List<ComplianceAgreementResponse>> getAll() {
        return new ResponseEntity<>(complianceAgreementService.getAll().stream()
                .map(complianceAgreementMapper::complianceAgreementToResponse)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ComplianceAgreementResponse> create(@RequestBody ComplianceAgreementCreateRequest request) {
        ComplianceAgreement compliance = complianceAgreementService.create(complianceAgreementMapper.requestToComplianceAgreement(request));

        return new ResponseEntity<>(
                complianceAgreementMapper.complianceAgreementToResponse(compliance),
                HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody ComplianceAgreementDeleteRequest request) {
        complianceAgreementService.delete(request.getComplianceAgreementId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
