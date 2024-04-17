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
    IC_202("User not found", HttpStatus.BAD_REQUEST);

    private final String description;
    private final HttpStatus status;
}
