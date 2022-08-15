package com.colendi.assignment.colendiapi.persistence.entity;

import com.colendi.assignment.colendiapi.model.enums.CardStatusEnum;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CardEntity {

    @Id
    @GeneratedValue
    private Long cardId;

    @Column(nullable = false)
    private Long userId;

    private Long accountId;

    @Column(nullable = false)
    private String cardNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CardStatusEnum cardActive;

    private BigDecimal cardBalance;

    @Column(nullable = false)
    private LocalDateTime expireDate;

    private int cvv;

    private String allowedMerchantCategories;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime createDate;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime lastTransactionDate;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime updateDate;

}
