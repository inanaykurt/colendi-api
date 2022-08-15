package com.colendi.assignment.colendiapi.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    private Long accountId;

    private BigDecimal totalBalance;
}
