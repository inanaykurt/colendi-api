package com.colendi.assignment.colendiapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUserRequest {

    @NotBlank(message = "Name is mandatory")
    private String firstName;
    @NotBlank(message = "Surname is mandatory")
    private String surName;
    @Email(message = "Email is not valid")
    @NotBlank(message = "Email is mandatory")
    private String email;
}
