package com.colendi.assignment.colendiapi.controller;

import com.colendi.assignment.colendiapi.model.CreateMerchantRequest;
import com.colendi.assignment.colendiapi.model.CreateUserRequest;
import com.colendi.assignment.colendiapi.model.MerchantDTO;
import com.colendi.assignment.colendiapi.service.MerchantService;
import com.colendi.assignment.colendiapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/merchants")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;

    @PostMapping
    public MerchantDTO createMerchant(@RequestBody CreateMerchantRequest createMerchantRequest) {

        return merchantService.createUser(createMerchantRequest);

    }

    @GetMapping
    public List<MerchantDTO> retrieveMerchants() {

        return merchantService.retrieveMerchants();

    }
}
