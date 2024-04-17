package com.itapp.inventorycontrol.dto.response;

import com.itapp.inventorycontrol.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponse {
    private Long id;
    private String name;
    private String surname;
    private String phone;
    private String email;
    private UserRole role;
}
