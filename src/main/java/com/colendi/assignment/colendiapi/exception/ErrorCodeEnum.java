package com.colendi.assignment.colendiapi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {

    INTERNAL_SERVER_ERROR(1000, "Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR),
    FIELD_VALIDATION_ERROR(1001, "Field validation error.", HttpStatus.BAD_REQUEST),
    CONTENT_NOT_FOUND_ERROR(1002, "Content not found.", HttpStatus.BAD_REQUEST),
    INSUFFICIENT_BALANCE(1003, "Insufficient balance.", HttpStatus.BAD_REQUEST),
    NO_CARD_FOUND_ERROR(1004, "No card found.", HttpStatus.BAD_REQUEST),
    NO_ACTIVECARD_FOUND_ERROR(1004, "No active card found.", HttpStatus.BAD_REQUEST),
    CARD_ALREADY_CANCELED_ERROR(1005, "Card is already canceled.", HttpStatus.BAD_REQUEST),
    NO_ACCOUNT_FOUND(1006, "No Account Found.", HttpStatus.BAD_REQUEST),
    CARD_PASSIVE_FOUND(1007, "Passive Card is not allowed.", HttpStatus.BAD_REQUEST),
    CARD_IS_NOT_ACTIVE(1008, "Card is not active.", HttpStatus.BAD_REQUEST),
    CARD_EXPIRED(1009, "Card expired.", HttpStatus.BAD_REQUEST),
    CARD_NOT_ALLOWED_MERCHANT(1010 ,"Merchant is not allowed.", HttpStatus.BAD_REQUEST);

    private int code;
    private String message;
    private HttpStatus httpStatus;
}
