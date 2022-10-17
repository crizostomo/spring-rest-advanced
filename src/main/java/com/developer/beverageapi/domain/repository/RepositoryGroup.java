package com.developer.beverageapi.domain.repository;

import com.developer.beverageapi.domain.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryGroup extends JpaRepository<Group, Long> {

}
