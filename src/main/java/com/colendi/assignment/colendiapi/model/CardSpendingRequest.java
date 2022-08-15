package com.colendi.assignment.colendiapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardSpendingRequest {

    @NotNull(message = "merchantid is mandatory")
    private Long merchantId;
    @NotNull(message = "spendAmount is mandatory")
    private BigDecimal spendAmount;
    @NotNull(message = "cardNumber is mandatory")
    private String cardNumber;

}
