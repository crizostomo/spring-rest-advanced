package com.developer.beverageapi.domain.service;

import com.developer.beverageapi.domain.model.ProductPhoto;
import com.developer.beverageapi.domain.repository.RepositoryProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CatalogProductPhotoService {

    @Autowired
    private RepositoryProduct repositoryProduct;

    @Transactional
    public ProductPhoto save(ProductPhoto photo) {
        return repositoryProduct.save(photo);
    }
}
