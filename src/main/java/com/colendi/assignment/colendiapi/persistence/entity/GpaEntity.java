package com.colendi.assignment.colendiapi.persistence.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class GpaEntity {

    @Id
    @GeneratedValue
    @Column(name = "ACCOUNT_ID")
    private Long accountId;

    private Long userId;

    private Long merchantID;

    private BigDecimal totalBalance;

    @OneToOne(mappedBy = "account")
    private UserEntity user;

}
