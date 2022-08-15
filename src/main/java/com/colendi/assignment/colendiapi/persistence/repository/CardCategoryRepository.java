package com.colendi.assignment.colendiapi.persistence.repository;

import com.colendi.assignment.colendiapi.persistence.entity.CardEntity;
import com.colendi.assignment.colendiapi.persistence.entity.CardMCCProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CardCategoryRepository extends JpaRepository<CardMCCProfileEntity, Long> {

    @Query(value="select * from CARDMCCPROFILE_ENTITY a where a.profile_code= :code", nativeQuery=true)
    List<CardMCCProfileEntity> getAllowedMCCListByProfile(String code);
}
