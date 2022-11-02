package com.developer.beverageapi.domain.service;

import com.developer.beverageapi.domain.model.ProductPhoto;
import com.developer.beverageapi.domain.repository.RepositoryProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

@Service
public class CatalogProductPhotoService {

    @Autowired
    private RepositoryProduct repositoryProduct;

    @Autowired
    private PhotoStorageService photoStorageService;

    @Transactional
    public ProductPhoto save(ProductPhoto photo, InputStream filesData) {
//        Long restaurantId  = photo.getProduct().getRestaurant().getId();
        Long restaurantId  = photo.getRestaurantId();
        Long productId = photo.getProduct().getId();
        String newFileName = photoStorageService.generateFileName(photo.getFileName());
        String nameFileExisting = null;

        Optional<ProductPhoto> existingPhoto = repositoryProduct.findPhotoById(restaurantId, productId);
        if (existingPhoto.isPresent()) {
            nameFileExisting = existingPhoto.get().getFileName();
            repositoryProduct.delete(existingPhoto.get());
        }

        photo.setFileName(newFileName);
        photo = repositoryProduct.save(photo);
        repositoryProduct.flush();

        PhotoStorageService.NewPhoto newPhoto = PhotoStorageService.NewPhoto.builder()
                .fileName(photo.getFileName())
                .inputStream(filesData)
                .build();

//        if (nameFileExisting != null) {
//            photoStorageService.remove(nameFileExisting);
//        }
//        photoStorageService.storage(newPhoto);
        photoStorageService.substitute(nameFileExisting, newPhoto);

        return photo;
    }
}
