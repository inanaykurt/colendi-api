package com.colendi.assignment.colendiapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateCardDepositRequest {

    @NotNull(message = "deposit is mandatory")
    private BigDecimal deposit;
    @NotNull(message = "cardNumber is mandatory")
    private String cardNumber;

}
