package com.colendi.assignment.colendiapi.persistence.repository;

import com.colendi.assignment.colendiapi.persistence.entity.GpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<GpaEntity, Long> {

    @Query(value="select * from GPA_ENTITY a where a.user_id= :userid", nativeQuery=true)
    List<GpaEntity> getAccountsByUserId(long userid);
}
