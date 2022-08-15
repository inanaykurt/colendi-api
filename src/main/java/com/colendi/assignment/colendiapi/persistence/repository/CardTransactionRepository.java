package com.colendi.assignment.colendiapi.persistence.repository;

import com.colendi.assignment.colendiapi.persistence.entity.CardTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardTransactionRepository extends JpaRepository<CardTransactionEntity, Long> {
}
