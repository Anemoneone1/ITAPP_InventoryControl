package com.itapp.inventorycontrol.controller;

import com.itapp.inventorycontrol.dto.request.ItemCreateRequest;
import com.itapp.inventorycontrol.dto.request.ItemDeleteRequest;
import com.itapp.inventorycontrol.dto.request.ItemEditRequest;
import com.itapp.inventorycontrol.dto.response.ComplianceResponse;
import com.itapp.inventorycontrol.dto.response.CompliancesAndConditionsResponse;
import com.itapp.inventorycontrol.dto.response.ItemResponse;
import com.itapp.inventorycontrol.dto.response.StorageConditionResponse;
import com.itapp.inventorycontrol.entity.Item;
import com.itapp.inventorycontrol.mapper.ComplianceMapper;
import com.itapp.inventorycontrol.mapper.ItemMapper;
import com.itapp.inventorycontrol.mapper.StorageConditionMapper;
import com.itapp.inventorycontrol.service.ComplianceService;
import com.itapp.inventorycontrol.service.ItemService;
import com.itapp.inventorycontrol.service.StorageConditionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping(APIVersion.current + "/item")
public class ItemController {
    private final ItemService itemService;
    private final ComplianceService complianceService;
    private final StorageConditionService storageConditionService;
    private final ItemMapper itemMapper;
    private final ComplianceMapper complianceMapper;
    private final StorageConditionMapper storageConditionMapper;

    @GetMapping("/compliances-and-conditions")
    public ResponseEntity<CompliancesAndConditionsResponse> getCompliancesAndConditions() {
        List<ComplianceResponse> compliances = complianceService.getAll().stream()
                .map(complianceMapper::complianceToResponse).toList();

        List<StorageConditionResponse> storageConditions = storageConditionService.getAll().stream()
                .map(storageConditionMapper::StorageConditionToResponse).toList();

        return new ResponseEntity<>(
                new CompliancesAndConditionsResponse(compliances, storageConditions),
                HttpStatus.OK);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResponse> getById(@PathVariable("itemId") Long itemId) {
        Item item = itemService.getById(itemId);

        return new ResponseEntity<>(
                itemMapper.itemToResponse(item),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ItemResponse>> getAll() {
        return new ResponseEntity<>(itemService.getAll().stream()
                .map(itemMapper::itemToResponse)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ItemResponse> create(@RequestBody ItemCreateRequest request) {
        Item item = itemService.create(itemMapper.requestToItem(request), request.getComplianceIds(), request.getStorageConditionIds());

        return new ResponseEntity<>(
                itemMapper.itemToResponse(item),
                HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ItemResponse> edit(@RequestBody ItemEditRequest request) {
        Item item = itemService.edit(itemMapper.requestToItem(request), request.getComplianceIds(), request.getStorageConditionIds());

        return new ResponseEntity<>(
                itemMapper.itemToResponse(item),
                HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody ItemDeleteRequest request) {
        itemService.delete(request.getItemId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
