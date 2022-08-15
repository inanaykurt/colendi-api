package com.colendi.assignment.colendiapi;

import com.colendi.assignment.colendiapi.exception.ColendiApiException;
import com.colendi.assignment.colendiapi.exception.ErrorCodeEnum;
import com.colendi.assignment.colendiapi.model.CardDTO;
import com.colendi.assignment.colendiapi.model.CardTrxDTO;
import com.colendi.assignment.colendiapi.model.UpdateCardDepositRequest;
import com.colendi.assignment.colendiapi.persistence.entity.GpaEntity;
import com.colendi.assignment.colendiapi.persistence.entity.UserEntity;
import com.colendi.assignment.colendiapi.persistence.repository.AccountRepository;
import com.colendi.assignment.colendiapi.persistence.repository.CardRepository;
import com.colendi.assignment.colendiapi.persistence.repository.CardTransactionRepository;
import com.colendi.assignment.colendiapi.persistence.repository.UserRepository;
import com.colendi.assignment.colendiapi.service.CardService;
import com.colendi.assignment.colendiapi.service.CardTransactionService;
import com.colendi.assignment.colendiapi.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
class ColendiApiApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CardRepository cardRepository;

	@Autowired
	private CardService cardService;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private CardTransactionService cardTransactionService;

	@Test
	public void givenUser_whenGetUser_returnUserId(){
		UserEntity user = userRepository.findById(1L).orElse(null);
		Assertions.assertNotNull(user.getUserId());
	}

	@Test
	public void givenCard_whenGetCardsOfUser_returnCards(){
		List<CardDTO> cards = cardService.getCardsByUser(1L);
		Assertions.assertNotNull(cards.size());
		Assertions.assertEquals(cards.stream().findAny().get().getUserId(), 1L);
	}

	@Test
	public void givenUnknownCardNumber_whenCancelCard_throwException(){
		ColendiApiException exception = Assertions.assertThrows(ColendiApiException.class, () -> {
			cardService.cancelCardsByCardNumber("2222");
		});

		ErrorCodeEnum actualMessage = exception.getErrorCode();

		Assertions.assertTrue(actualMessage.compareTo(ErrorCodeEnum.NO_CARD_FOUND_ERROR) ==0);

	}


}
