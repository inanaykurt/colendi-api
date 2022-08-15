package com.colendi.assignment.colendiapi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {

    int errorCode;

    String errorMsg;

    HttpStatus status;
}
