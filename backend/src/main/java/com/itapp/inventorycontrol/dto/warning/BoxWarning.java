package com.itapp.inventorycontrol.dto.warning;

import com.itapp.inventorycontrol.entity.Box;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoxWarning {
    private Box box;
    private String reason;
}
