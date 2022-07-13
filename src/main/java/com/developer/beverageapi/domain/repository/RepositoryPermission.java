package com.developer.beverageapi.domain.repository;

import com.developer.beverageapi.domain.model.Permission;

import java.util.List;

public interface RepositoryPermission {

    List<Permission> listAll();
    Permission searchById(Long id);
    Permission add(Permission permission);
    void remove(Permission permission);
}
