package com.colendi.assignment.colendiapi.service;

import com.colendi.assignment.colendiapi.exception.ColendiApiException;
import com.colendi.assignment.colendiapi.exception.ErrorCodeEnum;
import com.colendi.assignment.colendiapi.model.CreateUserRequest;
import com.colendi.assignment.colendiapi.model.UserDTO;
import com.colendi.assignment.colendiapi.persistence.entity.GpaEntity;
import com.colendi.assignment.colendiapi.persistence.entity.UserEntity;
import com.colendi.assignment.colendiapi.persistence.repository.AccountRepository;
import com.colendi.assignment.colendiapi.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final AccountRepository accountRepository;

    @Transactional(rollbackFor = ColendiApiException.class )
    public UserDTO createUser(CreateUserRequest user) {

        LocalDateTime now = LocalDateTime.now()
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        UserEntity userEntity = UserEntity.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .surname(user.getSurName())
                .build();

        UserEntity savedUser = userRepository.save(userEntity);

        GpaEntity accountEntity = GpaEntity.builder()
                .user(savedUser)
                .userId(savedUser.getUserId())
                .totalBalance(BigDecimal.ZERO)
                .build();

        GpaEntity savedAccount = accountRepository.save(accountEntity);

        return UserDTO.builder()
                .accountId(savedAccount.getAccountId())
                .email(savedUser.getEmail())
                .firstName(savedUser.getFirstName())
                .surname(savedUser.getSurname())
                .userId(savedUser.getUserId())
                .balance(savedAccount.getTotalBalance())
                .build();
    }

    public UserDTO getUser(Long userId) {

        Optional<UserEntity> user = userRepository.findById(userId);

        if (user.isPresent()){
            List<GpaEntity> account = accountRepository.getAccountsByUserId(user.get().getUserId());

            return UserDTO.builder()
                    .userId(user.get().getUserId())
                    .firstName(user.get().getFirstName())
                    .surname(user.get().getSurname())
                    .email(user.get().getEmail())
                    .accountId(account.get(0).getAccountId())
                    .balance(account.get(0).getTotalBalance())
                    .build();

        }else{
            throw new ColendiApiException(ErrorCodeEnum.CONTENT_NOT_FOUND_ERROR);
        }
    }
}
