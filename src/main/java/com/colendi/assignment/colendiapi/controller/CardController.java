package com.colendi.assignment.colendiapi.controller;

import com.colendi.assignment.colendiapi.model.CardDTO;
import com.colendi.assignment.colendiapi.model.UserDTO;
import com.colendi.assignment.colendiapi.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;


    @PostMapping("/{userID}")
    public CardDTO createCard(@PathVariable Long userID) {

        return cardService.createCard(userID);

    }

    @PutMapping("/{cardNumber}")
    public CardDTO cancelCard(@PathVariable String cardNumber) {

        return cardService.cancelCardsByCardNumber(cardNumber);

    }

    @PutMapping("/card/{cardNumber}")
    public CardDTO reissueCard(@PathVariable String cardNumber) {

         return cardService.copyCardWithNewCVV(cardNumber);


    }
}
