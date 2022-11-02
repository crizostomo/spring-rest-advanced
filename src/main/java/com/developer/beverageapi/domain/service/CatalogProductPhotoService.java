package com.developer.beverageapi.domain.service;

import com.developer.beverageapi.domain.model.ProductPhoto;
import com.developer.beverageapi.domain.repository.RepositoryProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CatalogProductPhotoService {

    @Autowired
    private RepositoryProduct repositoryProduct;

    @Transactional
    public ProductPhoto save(ProductPhoto photo) {
//        Long restaurantId  = photo.getProduct().getRestaurant().getId();
        Long restaurantId  = photo.getRestaurantId();
        Long productId = photo.getProduct().getId();

        Optional<ProductPhoto> existingPhoto = repositoryProduct.findPhotoById(restaurantId, productId);
        if (existingPhoto.isPresent()) {
            repositoryProduct.delete(existingPhoto.get());
        }

        return repositoryProduct.save(photo);
    }
}
