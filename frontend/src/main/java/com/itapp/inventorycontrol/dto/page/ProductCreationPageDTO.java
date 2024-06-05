package com.itapp.inventorycontrol.dto.page;

import com.itapp.inventorycontrol.dto.front.ItemDTO;
import com.itapp.inventorycontrol.dto.front.StorageSpaceDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductCreationPageDTO {
    private List<ItemDTO> itemList;
    private List<StorageSpaceDTO> storageList;
}
