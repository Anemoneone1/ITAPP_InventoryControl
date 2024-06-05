package com.itapp.inventorycontrol.dto.front;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateEmployeeDTO {
    private String email;
    private String password;
    private String phone;
    private String name;
    private String surname;
    private Long companyID;
}
