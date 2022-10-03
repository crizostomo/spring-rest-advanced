package com.developer.beverageapi.domain.repository;

import com.developer.beverageapi.domain.model.User;

import java.util.Optional;

public interface RepositoryUser extends CustomJpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
