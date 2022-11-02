package com.developer.beverageapi.infrastructure.repository;

import com.developer.beverageapi.domain.model.ProductPhoto;
import com.developer.beverageapi.domain.repository.RepositoryProductQueries;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class RepositoryProductImpl implements RepositoryProductQueries {

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    @Override
    public ProductPhoto save(ProductPhoto photo) {
        return manager.merge(photo);
    }

    @Transactional
    @Override
    public void delete(ProductPhoto photo) {
        manager.remove(photo);
    }
}
