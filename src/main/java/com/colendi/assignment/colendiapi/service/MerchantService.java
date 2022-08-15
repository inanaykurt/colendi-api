package com.colendi.assignment.colendiapi.service;

import com.colendi.assignment.colendiapi.exception.ColendiApiException;
import com.colendi.assignment.colendiapi.model.CreateMerchantRequest;
import com.colendi.assignment.colendiapi.model.CreateUserRequest;
import com.colendi.assignment.colendiapi.model.MerchantDTO;
import com.colendi.assignment.colendiapi.model.UserDTO;
import com.colendi.assignment.colendiapi.persistence.entity.GpaEntity;
import com.colendi.assignment.colendiapi.persistence.entity.MerchantEntity;
import com.colendi.assignment.colendiapi.persistence.entity.UserEntity;
import com.colendi.assignment.colendiapi.persistence.repository.AccountRepository;
import com.colendi.assignment.colendiapi.persistence.repository.MerchantRepository;
import com.colendi.assignment.colendiapi.persistence.repository.UserRepository;
import com.colendi.assignment.colendiapi.util.GenerateMerchantId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.cache.annotation.Cacheable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantService {

    private final MerchantRepository merchantRepository;

    private final AccountRepository accountRepository;

    @Transactional(rollbackFor = ColendiApiException.class )
    public MerchantDTO createUser(CreateMerchantRequest merchant) {

        long miid = GenerateMerchantId.generateRandomNum();
        MerchantEntity merchantEntity = MerchantEntity.builder()
                .merchantId(miid)
                .merchantCategoryCode(merchant.getMerchantCategoryCode())
                .build();

        GpaEntity accountEntity = GpaEntity.builder()
                .totalBalance(BigDecimal.ZERO)
                .merchantID(miid)
                .build();
        GpaEntity savedAccount = accountRepository.save(accountEntity);
        merchantEntity.setAccountId(accountEntity.getAccountId());
        MerchantEntity savedMerchant = merchantRepository.save(merchantEntity);

        return MerchantDTO.builder()
                .accountId(savedAccount.getAccountId())
                .merchantCategoryCode(savedMerchant.getMerchantCategoryCode())
                .merchantId(miid)
                .build();
    }

    public MerchantDTO getMerchant(Long merchantId) {

        MerchantEntity merchantEntity = merchantRepository.getOne(merchantId);

        return MerchantDTO.builder()
                .accountId(merchantEntity.getAccountId())
                .merchantCategoryCode(merchantEntity.getMerchantCategoryCode())
                .merchantId(merchantEntity.getMerchantId())
                .build();
    }

    @Cacheable("merchants")
    public List<MerchantDTO> retrieveMerchants() {

        List<MerchantDTO> merchantList = new ArrayList<>();
        merchantRepository.findAll()
                .forEach(merchantEntity -> merchantList.add(MerchantDTO.builder()
                        .merchantCategoryCode(merchantEntity.getMerchantCategoryCode())
                        .merchantId(merchantEntity.getMerchantId())
                        .accountId(merchantEntity.getAccountId())
                .build()));

        return merchantList;

    }
}
