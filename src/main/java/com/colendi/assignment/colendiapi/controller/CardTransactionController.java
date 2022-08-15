package com.colendi.assignment.colendiapi.controller;

import com.colendi.assignment.colendiapi.model.CardDTO;
import com.colendi.assignment.colendiapi.model.CardSpendingRequest;
import com.colendi.assignment.colendiapi.model.CardTrxDTO;
import com.colendi.assignment.colendiapi.model.UpdateCardDepositRequest;
import com.colendi.assignment.colendiapi.service.CardTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@RequestMapping(path = "/card-transaction")
@RequiredArgsConstructor
public class CardTransactionController {

    private final CardTransactionService cardTransactionService;

    @PostMapping("/deposit/{balance}")
    public CardTrxDTO depositCard(@Valid @RequestBody UpdateCardDepositRequest cardDepositRequest) {

        return cardTransactionService.depositMoney(cardDepositRequest);

    }

    @PostMapping("/spend")
    public CardTrxDTO cardSpending(@Valid @RequestBody CardSpendingRequest cardSpendingRequest) {

        return cardTransactionService.spendMoney(cardSpendingRequest);
    }

}
