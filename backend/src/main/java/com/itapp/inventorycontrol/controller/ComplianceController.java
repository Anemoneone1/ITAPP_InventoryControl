package com.itapp.inventorycontrol.controller;

import com.itapp.inventorycontrol.dto.request.ComplianceCreateRequest;
import com.itapp.inventorycontrol.dto.request.ComplianceDeleteRequest;
import com.itapp.inventorycontrol.dto.response.ComplianceHistoryResponse;
import com.itapp.inventorycontrol.dto.response.ComplianceResponse;
import com.itapp.inventorycontrol.entity.Compliance;
import com.itapp.inventorycontrol.mapper.ComplianceMapper;
import com.itapp.inventorycontrol.service.ComplianceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping(APIVersion.current + "/compliance")
public class ComplianceController {
    private final ComplianceService complianceService;
    private final ComplianceMapper complianceMapper;

    @GetMapping
    public ResponseEntity<List<ComplianceResponse>> getAll() {
        return new ResponseEntity<>(complianceService.getAll().stream()
                .map(complianceMapper::complianceToResponse)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<List<ComplianceHistoryResponse>> getHistory() {
        return new ResponseEntity<>(complianceService.getAllWithAgreement().stream()
                .map(complianceMapper::complianceToHistoryResponse)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ComplianceResponse> create(@RequestBody ComplianceCreateRequest request) {
        Compliance compliance = complianceService.create(complianceMapper.requestToCompliance(request));

        return new ResponseEntity<>(
                complianceMapper.complianceToResponse(compliance),
                HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody ComplianceDeleteRequest request) {
        complianceService.delete(request.getComplianceId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
