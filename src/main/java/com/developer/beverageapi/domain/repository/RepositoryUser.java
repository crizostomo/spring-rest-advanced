package com.developer.beverageapi.domain.repository;

import com.developer.beverageapi.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryUser extends JpaRepository<User, Long> {

}
