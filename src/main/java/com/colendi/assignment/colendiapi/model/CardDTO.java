package com.colendi.assignment.colendiapi.model;

import com.colendi.assignment.colendiapi.model.enums.CardStatusEnum;
import com.colendi.assignment.colendiapi.persistence.entity.CardEntity;
import com.colendi.assignment.colendiapi.persistence.entity.CardMCCProfileEntity;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardDTO {

    private Long userId;

    private Long accountId;

    private String cardNumber;

    private CardStatusEnum cardActive;

    private BigDecimal cardBalance;

    private LocalDateTime expireDate;

    private int cvv;

    private String allowedMerchantCategories;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    private LocalDateTime lastTransactionDate;

    public static CardDTO mapToCardDTO(CardEntity cardEntity) {

        return CardDTO.builder()
                .userId(cardEntity.getUserId())
                .accountId(cardEntity.getAccountId())
                .cardNumber(cardEntity.getCardNumber())
                .cardActive(cardEntity.getCardActive())
                .cardBalance(cardEntity.getCardBalance())
                .expireDate(cardEntity.getExpireDate())
                .createDate(cardEntity.getCreateDate())
                .updateDate(cardEntity.getUpdateDate())
                .cvv(cardEntity.getCvv())
                .allowedMerchantCategories(cardEntity.getAllowedMerchantCategories())
                .lastTransactionDate(cardEntity.getLastTransactionDate())
                .build();
    }
}
