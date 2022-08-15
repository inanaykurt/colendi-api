package com.colendi.assignment.colendiapi.handler;

import com.colendi.assignment.colendiapi.persistence.entity.CardEntity;

import java.math.BigDecimal;
import java.util.List;

public interface ICardTransactionHandler {

    void processRequest(List<CardEntity> cards, BigDecimal amount);
    void setNext(ICardTransactionHandler handler);
}
