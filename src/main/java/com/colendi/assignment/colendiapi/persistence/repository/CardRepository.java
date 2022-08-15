package com.colendi.assignment.colendiapi.persistence.repository;

import com.colendi.assignment.colendiapi.persistence.entity.CardEntity;
import com.colendi.assignment.colendiapi.persistence.entity.GpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CardRepository extends JpaRepository<CardEntity, Long> {

    @Query(value="select * from CARD_ENTITY a where a.user_id= :userid", nativeQuery=true)
    List<CardEntity> getCardsByUserId(long userid);

    @Query(value="select * from CARD_ENTITY a where a.CARD_NUMBER = :cardnumber", nativeQuery=true)
    List<CardEntity> getCardsByCardNumber(String cardnumber);

}
