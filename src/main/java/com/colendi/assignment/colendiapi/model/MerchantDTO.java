package com.colendi.assignment.colendiapi.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MerchantDTO {

    private Long merchantId;

    private Long accountId;

    private String merchantCategoryCode;
}
