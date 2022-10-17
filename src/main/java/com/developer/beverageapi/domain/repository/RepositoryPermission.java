package com.developer.beverageapi.domain.repository;

import com.developer.beverageapi.domain.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryPermission extends JpaRepository<Permission, Long> {

}
