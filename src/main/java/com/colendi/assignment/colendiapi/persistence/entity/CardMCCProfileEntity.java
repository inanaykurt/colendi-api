package com.colendi.assignment.colendiapi.persistence.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Builder
@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CardMCCProfileEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String profileCode;

    @Column(nullable = false)
    private String mccCode;

}
