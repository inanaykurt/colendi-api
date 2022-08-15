package com.colendi.assignment.colendiapi.service;

import com.colendi.assignment.colendiapi.exception.ColendiApiException;
import com.colendi.assignment.colendiapi.exception.ErrorCodeEnum;
import com.colendi.assignment.colendiapi.model.CardDTO;
import com.colendi.assignment.colendiapi.model.enums.CardStatusEnum;
import com.colendi.assignment.colendiapi.persistence.entity.CardEntity;
import com.colendi.assignment.colendiapi.persistence.entity.CardMCCProfileEntity;
import com.colendi.assignment.colendiapi.persistence.entity.GpaEntity;
import com.colendi.assignment.colendiapi.persistence.repository.AccountRepository;
import com.colendi.assignment.colendiapi.persistence.repository.CardCategoryRepository;
import com.colendi.assignment.colendiapi.persistence.repository.CardRepository;

import com.colendi.assignment.colendiapi.util.GenerateRandomCVV;
import com.colendi.assignment.colendiapi.util.RandomCardNumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;

    private final AccountRepository accountRepository;

    private final CardCategoryRepository cardCategoryRepository;

    public List<CardDTO> getCardsByUser(Long userId) {

        List<CardEntity> cardEntityList = cardRepository.getCardsByUserId(userId);

        if (!cardEntityList.isEmpty()){

            List<CardDTO> cards = new ArrayList<>();
            cardEntityList.forEach(cardEntity -> cards.add(CardDTO.mapToCardDTO(cardEntity)));
            return cards;

        }else{
            throw new ColendiApiException(ErrorCodeEnum.CONTENT_NOT_FOUND_ERROR);
        }
    }

    public CardDTO cancelCardsByCardNumber(String cardNumber) {

        List<CardEntity> cardEntities = cardRepository.getCardsByCardNumber(cardNumber);

        if(cardEntities == null || cardEntities.isEmpty())
            throw new ColendiApiException(ErrorCodeEnum.NO_CARD_FOUND_ERROR);

        if(!cardEntities
                .stream()
                .findAny()
                .filter(cards -> cards.getCardActive().equals(CardStatusEnum.ACTIVE))
                .isPresent()) {
            throw new ColendiApiException(ErrorCodeEnum.NO_ACTIVECARD_FOUND_ERROR);
        }

        CardEntity activeCard = cardEntities.stream()
                .findAny()
                .filter(cards -> cards.getCardActive().equals(CardStatusEnum.ACTIVE)).get();

        activeCard.setCardActive(CardStatusEnum.PASSIVE);
        activeCard.setUpdateDate(LocalDateTime.now());
        CardEntity saved = cardRepository.save(activeCard);

        return CardDTO.mapToCardDTO(saved);

    }

    public CardDTO createCard(Long userID) {

        List<GpaEntity> account = accountRepository.getAccountsByUserId(userID);

        List<CardMCCProfileEntity> allowedMCCList = cardCategoryRepository.getAllowedMCCListByProfile("default");
        String mccList = String.join("-",allowedMCCList.stream().map(entity -> entity.getMccCode()).collect(Collectors.toList()));

        if(account.isEmpty())
            throw new ColendiApiException(ErrorCodeEnum.NO_ACCOUNT_FOUND);

        String cardNumber = RandomCardNumberGenerator.generateMasterCardNumber();

        CardEntity card = CardEntity.builder()
                .cardActive(CardStatusEnum.ACTIVE)
                .cvv(GenerateRandomCVV.generateRandomNum())
                .createDate(LocalDateTime.now())
                .expireDate(LocalDateTime.now().plusYears(2))
                .userId(userID)
                .cardNumber(cardNumber)
                .cardBalance(BigDecimal.ZERO)
                .accountId(account.get(0).getAccountId())
                .allowedMerchantCategories(mccList)
                .build();

        CardEntity newCard = cardRepository.save(card);

        return CardDTO.mapToCardDTO(newCard);

    }

    public CardDTO copyCardWithNewCVV(String cardNumber) {

        List<CardEntity> cards = cardRepository.getCardsByCardNumber(cardNumber);

        if(cards.isEmpty()){
            throw new ColendiApiException(ErrorCodeEnum.NO_CARD_FOUND_ERROR);
        }

        //cancel ex card with same card numbers if exists
        for(CardEntity oldCard : cards) {
            if(oldCard.getCardActive().equals(CardStatusEnum.ACTIVE)){
                oldCard.setCardActive(CardStatusEnum.PASSIVE);
                oldCard.setUpdateDate(LocalDateTime.now());
                cardRepository.save(oldCard);
            }
        }

        //get last card created
        List<CardEntity> sortedList = cards.stream()
                .sorted(Comparator.comparing(CardEntity::getExpireDate).reversed())
                .collect(Collectors.toList());

        //issue new card with copied values
        CardEntity newCard = CardEntity.builder()
                .cardActive(CardStatusEnum.ACTIVE)
                .cvv(GenerateRandomCVV.generateRandomNum())
                .createDate(LocalDateTime.now())
                .expireDate(LocalDateTime.now().plusYears(2))
                .userId(sortedList.get(0).getUserId())
                .cardNumber(sortedList.get(0).getCardNumber())
                .cardBalance(sortedList.get(0).getCardBalance())
                .accountId(sortedList.get(0).getAccountId())
                .allowedMerchantCategories(sortedList.get(0).getAllowedMerchantCategories())
                .build();

        cardRepository.save(newCard);
        return CardDTO.mapToCardDTO(newCard);


    }
}
