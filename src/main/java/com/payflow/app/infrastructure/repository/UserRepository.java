package com.payflow.app.infrastructure.repository;

import com.payflow.app.domain.entities.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Long, Users> {
}
