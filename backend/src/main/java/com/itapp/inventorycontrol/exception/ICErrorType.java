package com.itapp.inventorycontrol.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ICErrorType {
    IC_999("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    IC_998("Incorrect request data: ", HttpStatus.BAD_REQUEST),

    // token
    IC_101("Invalid token", HttpStatus.BAD_REQUEST),
    IC_102("Bad credentials", HttpStatus.BAD_REQUEST),

    // users
    IC_201("User not found", HttpStatus.NOT_FOUND),

    // warehouse
    IC_301("Warehouse not found", HttpStatus.NOT_FOUND),
    IC_302("Not enough free space at warehouse", HttpStatus.NOT_FOUND);

    private final String description;
    private final HttpStatus status;
}
