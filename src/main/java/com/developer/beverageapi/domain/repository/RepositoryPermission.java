package com.developer.beverageapi.domain.repository;

import com.developer.beverageapi.domain.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryPermission extends JpaRepository<Permission, Long> {

}
