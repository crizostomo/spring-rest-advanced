package com.developer.beverageapi.domain.repository;

import com.developer.beverageapi.domain.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryGroup extends JpaRepository<Group, Long> {

}
