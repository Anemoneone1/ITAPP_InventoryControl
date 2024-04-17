package com.itapp.inventorycontrol.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeCreateRequest {
    private String name;
    private String surname;
    private String phone;
    private String email;
    private String password;
}
