package com.colendi.assignment.colendiapi.persistence.repository;

import com.colendi.assignment.colendiapi.persistence.entity.MerchantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<MerchantEntity, Long> {
}
