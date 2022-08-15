package com.colendi.assignment.colendiapi.service;

import com.colendi.assignment.colendiapi.exception.ColendiApiException;
import com.colendi.assignment.colendiapi.exception.ErrorCodeEnum;
import com.colendi.assignment.colendiapi.handler.CardBalanceHandler;
import com.colendi.assignment.colendiapi.handler.CardNumberHandler;
import com.colendi.assignment.colendiapi.handler.MerchantListHandler;
import com.colendi.assignment.colendiapi.model.CardSpendingRequest;
import com.colendi.assignment.colendiapi.model.CardTrxDTO;
import com.colendi.assignment.colendiapi.model.UpdateCardDepositRequest;
import com.colendi.assignment.colendiapi.model.enums.CardStatusEnum;
import com.colendi.assignment.colendiapi.model.enums.TransactionEnum;
import com.colendi.assignment.colendiapi.persistence.entity.*;
import com.colendi.assignment.colendiapi.persistence.repository.AccountRepository;
import com.colendi.assignment.colendiapi.persistence.repository.CardRepository;
import com.colendi.assignment.colendiapi.persistence.repository.CardTransactionRepository;
import com.colendi.assignment.colendiapi.persistence.repository.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CardTransactionService {

    private final CardRepository cardRepository;

    private final MerchantRepository merchantRepository;

    private final AccountRepository accountRepository;

    private final CardTransactionRepository cardTransactionRepository;

    @Transactional(rollbackFor = ColendiApiException.class )
    public CardTrxDTO spendMoney(CardSpendingRequest spendRequest){

        List<CardEntity> cards = cardRepository.getCardsByCardNumber(spendRequest.getCardNumber());

        //check business requirements
        CardNumberHandler cardNumberHandler = new CardNumberHandler();
        MerchantListHandler merchantListHandler = new MerchantListHandler();
        CardBalanceHandler cardBalanceHandler = new CardBalanceHandler();
        cardNumberHandler.processRequest(cards, spendRequest.getSpendAmount());
        cardBalanceHandler.processRequest(cards, spendRequest.getSpendAmount());


        CardEntity suitableCard = cards.stream()
                .filter(card -> card.getCardActive().equals(CardStatusEnum.ACTIVE))
                .filter(card -> card.getExpireDate().compareTo(LocalDateTime.now()) >= 0)
                .filter(card -> card.getCardBalance().compareTo(spendRequest.getSpendAmount()) >= 0)
                .findFirst().get();


        Optional<MerchantEntity> merchant = merchantRepository.findById(spendRequest.getMerchantId());
        if(!merchant.isPresent()){
            throw new ColendiApiException(ErrorCodeEnum.CONTENT_NOT_FOUND_ERROR);
        }
        merchantListHandler.processRequest(suitableCard, merchant.get());

        //update merchant balance
        GpaEntity merchantAccountEntity = accountRepository.getOne(merchant.get().getAccountId());
        merchantAccountEntity.setTotalBalance(merchantAccountEntity.getTotalBalance().add(spendRequest.getSpendAmount()));

        //update user balance
        suitableCard.setCardBalance(suitableCard.getCardBalance().subtract(spendRequest.getSpendAmount()));
        suitableCard.setLastTransactionDate(LocalDateTime.now());

        //transaction log
        CardTransactionEntity trx = CardTransactionEntity.builder()
                .transactionDate(LocalDateTime.now())
                .cardNumber(suitableCard.getCardNumber())
                .transactionType(TransactionEnum.SPENDING)
                .amount(spendRequest.getSpendAmount())
                .build();

        accountRepository.save(merchantAccountEntity);
        cardRepository.save(suitableCard);
        cardTransactionRepository.save(trx);

        return CardTrxDTO.mapToCardTrxDTO(trx);


    }

    @Transactional(rollbackFor = ColendiApiException.class )
    public CardTrxDTO depositMoney(UpdateCardDepositRequest depositRequest){

        List<CardEntity> cards = cardRepository.getCardsByCardNumber(depositRequest.getCardNumber());

        //business requirements
        CardNumberHandler cardNumberHandler = new CardNumberHandler();
        cardNumberHandler.processRequest(cards, depositRequest.getDeposit());

        CardEntity suitableCard = cards.stream()
                .filter(card -> card.getCardActive().equals(CardStatusEnum.ACTIVE))
                .filter(card -> card.getExpireDate().compareTo(LocalDateTime.now()) >= 0)
                .findFirst().get();

        suitableCard.setCardBalance(suitableCard.getCardBalance().add(depositRequest.getDeposit()));
        suitableCard.setLastTransactionDate(LocalDateTime.now());

        GpaEntity account = accountRepository.getOne(suitableCard.getAccountId());
        account.setTotalBalance(account.getTotalBalance().add(depositRequest.getDeposit()));

        cardRepository.save(suitableCard);
        accountRepository.save(account);

        //transaction log
        CardTransactionEntity trx = CardTransactionEntity.builder()
                .transactionDate(LocalDateTime.now())
                .cardNumber(depositRequest.getCardNumber())
                .transactionType(TransactionEnum.DEPOSIT)
                .amount(depositRequest.getDeposit())
                .build();

        cardTransactionRepository.save(trx);

        return CardTrxDTO.mapToCardTrxDTO(trx);
    }

}


