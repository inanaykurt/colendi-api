package com.colendi.assignment.colendiapi.model;

import com.colendi.assignment.colendiapi.model.enums.CardStatusEnum;
import com.colendi.assignment.colendiapi.model.enums.TransactionEnum;
import com.colendi.assignment.colendiapi.persistence.entity.CardEntity;
import com.colendi.assignment.colendiapi.persistence.entity.CardTransactionEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardTrxDTO {

    @Id
    @GeneratedValue
    private Long transactionId;

    @Column(nullable = false)
    private String cardNumber;

    @Enumerated
    @Column(nullable = false)
    private TransactionEnum transactionType;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime transactionDate;

    public static CardTrxDTO mapToCardTrxDTO(CardTransactionEntity trxEntity) {

        return CardTrxDTO.builder()
                .cardNumber(trxEntity.getCardNumber())
                .transactionId(trxEntity.getTransactionId())
                .amount(trxEntity.getAmount())
                .transactionDate(trxEntity.getTransactionDate())
                .transactionType(trxEntity.getTransactionType())
                .build();
    }
}
