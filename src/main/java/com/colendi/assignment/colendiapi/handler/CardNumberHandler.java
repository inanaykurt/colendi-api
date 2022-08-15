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

public class CardNumberHandler implements ICardTransactionHandler {

    private ICardTransactionHandler next;

    @Override
    public void processRequest(List<CardEntity> cards, BigDecimal amount) {

        if(cards == null || cards.size() == 0){
            throw new ColendiApiException(ErrorCodeEnum.NO_CARD_FOUND_ERROR);
        }
        if(!cards.stream().filter(card -> card.getCardActive().equals(CardStatusEnum.ACTIVE)).findFirst().isPresent()){
            throw new ColendiApiException(ErrorCodeEnum.CARD_IS_NOT_ACTIVE);
        }
        if(!cards.stream().filter(card -> card.getExpireDate().compareTo(LocalDateTime.now()) >= 0).findFirst().isPresent()){
            throw new ColendiApiException(ErrorCodeEnum.CARD_EXPIRED);
        }

    }

    @Override
    public void setNext(ICardTransactionHandler next) {
        this.next = next;
    }
}
