package com.itapp.inventorycontrol.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoxResponse {
    private Long id;
    private String uuid;
    private Long storageId;
    private String serialNumber;
    private List<BoxItemResponse> items;
}
