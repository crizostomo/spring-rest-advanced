package com.developer.beverageapi.domain.service;

import com.developer.beverageapi.domain.exception.PermissionNotFoundException;
import com.developer.beverageapi.domain.model.Permission;
import com.developer.beverageapi.domain.repository.RepositoryPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionRegistrationService {

    @Autowired
    private RepositoryPermission repositoryPermission;

    public Permission searchOrFail(Long permissionId) {
        return repositoryPermission.findById(permissionId)
                .orElseThrow(() -> new PermissionNotFoundException(permissionId));
    }
}
