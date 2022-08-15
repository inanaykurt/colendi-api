package com.colendi.assignment.colendiapi.util;

import com.colendi.assignment.colendiapi.model.enums.CardStatusEnum;
import com.colendi.assignment.colendiapi.persistence.entity.*;
import com.colendi.assignment.colendiapi.persistence.repository.*;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DBInitializer {

    private final UserRepository userRepository;

    private final AccountRepository accountRepository;

    private final CardRepository cardRepository;

    private final MerchantRepository merchantRepository;

    private final CardCategoryRepository cardCategories;

    @PostConstruct
    private void postConstruct() {

        LocalDateTime now = LocalDateTime.now()
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        UserEntity user = createUser(now);
        createCards(user, now);
        createCardCateogories();
        createMerchants();

    }

    private void createCardCateogories() {
        CardMCCProfileEntity p1 = CardMCCProfileEntity.builder()
                .mccCode("1")
                .profileCode("default")
                .build();
        CardMCCProfileEntity p2 = CardMCCProfileEntity.builder()
                .mccCode("2")
                .profileCode("default")
                .build();
        CardMCCProfileEntity p3 = CardMCCProfileEntity.builder()
                .mccCode("2")
                .profileCode("profile2")
                .build();
        CardMCCProfileEntity p4 = CardMCCProfileEntity.builder()
                .mccCode("4")
                .profileCode("profile2")
                .build();
        cardCategories.saveAll(Arrays.asList(p1, p2, p3,p4));
    }

    private void createCards(UserEntity user1, LocalDateTime now) {
        CardEntity card1 = CardEntity.builder()
                .cardActive(CardStatusEnum.ACTIVE)
                .cvv(522)
                .createDate(now)
                .expireDate(now.plusYears(1))
                .userId(user1.getUserId())
                .cardNumber("1234")
                .accountId(user1.getAccount().getAccountId())
                .build();

        CardEntity card2 = CardEntity.builder()
                .cardActive(CardStatusEnum.ACTIVE)
                .cvv(999)
                .createDate(now)
                .expireDate(now.plusYears(2))
                .userId(user1.getUserId())
                .cardNumber("1234567")
                .accountId(user1.getAccount().getAccountId())
                .build();
        card1.setUserId(user1.getUserId());
        card2.setUserId(user1.getUserId());


        cardRepository.saveAll(Arrays.asList(card1,card2));
    }

    private UserEntity createUser(LocalDateTime now) {
        UserEntity user1 = UserEntity.builder()
                .firstName("İnan")
                .surname("Aykurt")
                .email("inanaykurt@gmail.com")
                .build();

        GpaEntity account1 = GpaEntity.builder()
                .totalBalance(BigDecimal.valueOf(500))
                .build();

        account1.setUser(user1);
        user1.setAccount(account1);

        userRepository.save(user1);
        account1.setUserId(user1.getUserId());

        accountRepository.save(account1);

        return user1;
    }

    private void createMerchants() {

        long miid = GenerateMerchantId.generateRandomNum();
        MerchantEntity merchantEntity = MerchantEntity.builder()
                .merchantId(miid)
                .merchantCategoryCode("2")
                .build();

        GpaEntity accountEntity = GpaEntity.builder()
                .totalBalance(BigDecimal.ZERO)
                .merchantID(miid)
                .build();

        GpaEntity savedAccount = accountRepository.save(accountEntity);
        merchantEntity.setAccountId(accountEntity.getAccountId());
        MerchantEntity savedMerchant = merchantRepository.save(merchantEntity);

        long miid2 = GenerateMerchantId.generateRandomNum();
        MerchantEntity merchantEntity2 = MerchantEntity.builder()
                .merchantId(miid2)
                .merchantCategoryCode("6")
                .build();

        GpaEntity accountEntity2 = GpaEntity.builder()
                .totalBalance(BigDecimal.ZERO)
                .merchantID(miid)
                .build();

        GpaEntity savedAccount2 = accountRepository.save(accountEntity2);
        merchantEntity2.setAccountId(accountEntity2.getAccountId());
        MerchantEntity savedMerchant2 = merchantRepository.save(merchantEntity2);

    }
}
