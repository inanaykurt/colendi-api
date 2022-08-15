package com.colendi.assignment.colendiapi.controller;

import com.colendi.assignment.colendiapi.model.CardDTO;
import com.colendi.assignment.colendiapi.model.CreateUserRequest;
import com.colendi.assignment.colendiapi.model.UserDTO;
import com.colendi.assignment.colendiapi.service.CardService;
import com.colendi.assignment.colendiapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final CardService cardService;

    @PostMapping
    public UserDTO createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {

        return userService.createUser(createUserRequest);

    }

    @GetMapping("/{userId}")
    public UserDTO getUser(@PathVariable Long userId) {

        return userService.getUser(userId);

    }

    @GetMapping("/cards/{userId}")
    public List<CardDTO> getCardsByUser(@PathVariable Long userId) {

        return cardService.getCardsByUser(userId);

    }



}
