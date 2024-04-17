package com.itapp.inventorycontrol.mapper;

import com.itapp.inventorycontrol.dto.request.EmployeeCreateRequest;
import com.itapp.inventorycontrol.dto.request.UserCreateRequest;
import com.itapp.inventorycontrol.dto.response.UserResponse;
import com.itapp.inventorycontrol.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User requestToUser(UserCreateRequest request);

    User requestToUser(EmployeeCreateRequest request);

    UserResponse userToResponse(User user);
}
