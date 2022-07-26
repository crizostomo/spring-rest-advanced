package com.developer.beverageapi.infrasctructure.repository;

import com.developer.beverageapi.domain.model.Permission;
import com.developer.beverageapi.domain.repository.RepositoryPermission;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RepositoryPermissionImpl implements RepositoryPermission {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Permission> listAll(){
        return manager.createQuery("from Permission", Permission.class)
                .getResultList();
    }

    @Override
    public Permission searchById(Long id){
        return manager.find(Permission.class, id);
    }

    @Override
    @Transactional
    public Permission add(Permission permission){
        return manager.merge(permission);
    }

    @Override
    @Transactional
    public void remove(Permission permission){
        permission = searchById(permission.getId());
        manager.remove(permission);
    }
}
