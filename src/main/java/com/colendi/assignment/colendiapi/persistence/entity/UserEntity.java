package com.colendi.assignment.colendiapi.persistence.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Long userId;

    private String firstName;

    private String surname;

    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private GpaEntity account;


}
