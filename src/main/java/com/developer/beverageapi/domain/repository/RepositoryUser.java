package com.developer.beverageapi.domain.repository;

import com.developer.beverageapi.domain.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositoryUser extends CustomJpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
