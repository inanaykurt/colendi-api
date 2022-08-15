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
public class CreateMerchantRequest {

    @NotNull(message = "merchant name is mandatory")
    private String name;
    @NotNull(message = "merchant category is mandatory")
    private String merchantCategoryCode;
}
