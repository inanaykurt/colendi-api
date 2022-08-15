package com.colendi.assignment.colendiapi.model;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private Integer code;
    private String message;
}
