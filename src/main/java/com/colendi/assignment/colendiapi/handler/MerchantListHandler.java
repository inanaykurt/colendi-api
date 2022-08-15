package com.colendi.assignment.colendiapi.handler;

import com.colendi.assignment.colendiapi.exception.ColendiApiException;
import com.colendi.assignment.colendiapi.exception.ErrorCodeEnum;
import com.colendi.assignment.colendiapi.model.CardSpendingRequest;
import com.colendi.assignment.colendiapi.persistence.entity.CardEntity;
import com.colendi.assignment.colendiapi.persistence.entity.MerchantEntity;

import java.util.Arrays;


public class MerchantListHandler {

    public MerchantEntity processRequest(CardEntity suitableCard, MerchantEntity merchant) {

        String allowedCategories = suitableCard.getAllowedMerchantCategories();

        if(!Arrays.asList(allowedCategories.split("-")).contains(merchant.getMerchantCategoryCode())){
            throw new ColendiApiException(ErrorCodeEnum.CARD_NOT_ALLOWED_MERCHANT);
        }

        return merchant;
    }
}
