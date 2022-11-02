package com.developer.beverageapi.domain.repository;

import com.developer.beverageapi.domain.model.ProductPhoto;

public interface RepositoryProductQueries {

    ProductPhoto save(ProductPhoto photo);

    void delete(ProductPhoto photo);
}
