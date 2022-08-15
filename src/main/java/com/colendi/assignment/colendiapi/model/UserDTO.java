package com.colendi.assignment.colendiapi.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long userId;

    private String firstName;

    private String surname;

    private String email;

    private Long accountId;

    private BigDecimal balance;
}
