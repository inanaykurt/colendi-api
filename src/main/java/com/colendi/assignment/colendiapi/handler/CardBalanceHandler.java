package com.colendi.assignment.colendiapi.handler;

import com.colendi.assignment.colendiapi.exception.ColendiApiException;
import com.colendi.assignment.colendiapi.exception.ErrorCodeEnum;
import com.colendi.assignment.colendiapi.model.enums.CardStatusEnum;
import com.colendi.assignment.colendiapi.persistence.entity.CardEntity;
import com.colendi.assignment.colendiapi.persistence.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class CardBalanceHandler implements ICardTransactionHandler {

    @Autowired
    private CardRepository cardRepository;

    private ICardTransactionHandler next;

    @Override
    public void processRequest(List<CardEntity> cards, BigDecimal amount) {
        if(!cards.stream().filter(card -> card.getCardBalance().compareTo(amount) >= 0).findFirst().isPresent()){
            throw new ColendiApiException(ErrorCodeEnum.INSUFFICIENT_BALANCE);
        }
    }

    @Override
    public void setNext(ICardTransactionHandler next) {
        this.next = next;
    }
}
