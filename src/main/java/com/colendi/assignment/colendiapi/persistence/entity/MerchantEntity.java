package com.colendi.assignment.colendiapi.persistence.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MerchantEntity {

    @Id
    @Column(nullable = false)
    private Long merchantId;

    @Column(nullable = false)
    private Long accountId;

    @Column(nullable = false)
    private String merchantCategoryCode;

}
