package com.colendi.assignment.colendiapi.persistence.entity;

import com.colendi.assignment.colendiapi.model.CardDTO;
import com.colendi.assignment.colendiapi.model.CardTrxDTO;
import com.colendi.assignment.colendiapi.model.enums.TransactionEnum;
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
public class CardTransactionEntity {

    @Id
    @GeneratedValue
    private Long transactionId;

    @Column(nullable = false)
    private String cardNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionEnum transactionType;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime transactionDate;

}
