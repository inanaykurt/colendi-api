package com.colendi.assignment.colendiapi.persistence.repository;

import com.colendi.assignment.colendiapi.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
