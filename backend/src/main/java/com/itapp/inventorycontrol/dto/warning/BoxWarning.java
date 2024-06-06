package com.itapp.inventorycontrol.dto.warning;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoxWarning {
    private Long boxId;
    private String reason;
}
