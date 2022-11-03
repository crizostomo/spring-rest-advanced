package com.developer.beverageapi.api.controller;

import com.developer.beverageapi.api.assembler.ProductInputDismantle;
import com.developer.beverageapi.api.assembler.ProductModelAssembler;
import com.developer.beverageapi.api.assembler.ProductPhotoModelAssembler;
import com.developer.beverageapi.api.model.ProductModel;
import com.developer.beverageapi.api.model.ProductPhotoModel;
import com.developer.beverageapi.api.model.input.ProductInput;
import com.developer.beverageapi.api.model.input.ProductPhotoInput;
import com.developer.beverageapi.domain.exception.BusinessException;
import com.developer.beverageapi.domain.model.Product;
import com.developer.beverageapi.domain.model.ProductPhoto;
import com.developer.beverageapi.domain.model.Restaurant;
import com.developer.beverageapi.domain.repository.RepositoryProduct;
import com.developer.beverageapi.domain.service.CatalogProductPhotoService;
import com.developer.beverageapi.domain.service.ProductRegistrationService;
import com.developer.beverageapi.domain.service.RestaurantRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products")
public class ControllerRestaurantProduct {

    @Autowired
    private RepositoryProduct repositoryProduct;

    @Autowired
    private RestaurantRegistrationService registrationRestaurant;

    @Autowired
    private ProductRegistrationService registrationProduct;

    @Autowired
    private ProductModelAssembler productModelAssembler;

    @Autowired
    private ProductInputDismantle productInputDismantle;

    @Autowired
    private CatalogProductPhotoService catalogProductPhoto;

    @Autowired
    private ProductRegistrationService productRegistration;

    @Autowired
    private ProductPhotoModelAssembler productPhotoModelAssembler;

    @GetMapping
    public List<ProductModel> list(@PathVariable Long restaurantId) {
        Restaurant restaurant = registrationRestaurant.searchOrFail(restaurantId);

        List<Product> allProducts = repositoryProduct.findProductsByRestaurant(restaurant);

        return productModelAssembler.toCollectionModel(allProducts);
    }

    @GetMapping("/active-inactive")
    public List<ProductModel> listActiveAndOrInactive(@PathVariable Long restaurantId,
                                                      @RequestParam(required = false) boolean includeInactive) {
        Restaurant restaurant = registrationRestaurant.searchOrFail(restaurantId);

        List<Product> allProducts = null;

        if (includeInactive) {
            allProducts = repositoryProduct.findProductsByRestaurant(restaurant);
        } else {
            allProducts = repositoryProduct.findActivesByRestaurant(restaurant);
        }


        return productModelAssembler.toCollectionModel(allProducts);
    }

    @GetMapping("/{productId}")
    public ProductModel search(@PathVariable Long restaurantId, @PathVariable Long productId) {
        Product product = registrationProduct.searchOrFail(restaurantId, productId);

        return productModelAssembler.toModel(product);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductModel add(@PathVariable Long restaurantId,
                            @RequestBody @Valid ProductInput productInput) {
        Restaurant restaurant = registrationRestaurant.searchOrFail(restaurantId);

        Product product = productInputDismantle.toDomainObject(productInput);
        product.setRestaurant(restaurant);

        product = registrationProduct.add(product);

        return productModelAssembler.toModel(product);
    }

    @PutMapping("/{productId}")
    public ProductModel update(@PathVariable Long restaurantId, @PathVariable Long productId,
                               @RequestBody @Valid ProductInput productInput) {
        Product currentProduct = registrationProduct.searchOrFail(restaurantId, productId);

        productInputDismantle.copyToDomainObject(productInput, currentProduct);

        currentProduct = registrationProduct.add(currentProduct);

        return productModelAssembler.toModel(currentProduct);
    }

    @GetMapping(path = "/{productId}/photo")
    public ProductPhotoModel searchPhoto(@PathVariable Long restaurantId, @PathVariable Long productId) {
        ProductPhoto productPhoto = catalogProductPhoto.searchOrFail(restaurantId, productId);

        return productPhotoModelAssembler.toModel(productPhoto);
    }

    @PutMapping(path = "/{productId}/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductPhotoModel updatePhoto(@PathVariable Long restaurantId, @PathVariable Long productId,
                                         @Valid ProductPhotoInput productPhotoInput) throws IOException {

//        var fileName = UUID.randomUUID().toString() + "_" + productPhotoInput.getFile().getOriginalFilename();
//
//        var filePhoto = Path.of("/Users/diogo/Documents", fileName);
//
//        System.out.println(productPhotoInput.getDescription());
//        System.out.println(filePhoto);
//        System.out.println(productPhotoInput.getFile().getContentType());
//
//        try {
//            productPhotoInput.getFile().transferTo(filePhoto);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

        Product product = productRegistration.searchOrFail(restaurantId, productId);

        MultipartFile file = productPhotoInput.getFile();

        ProductPhoto photo = new ProductPhoto();
        photo.setProduct(product);
        photo.setDescription(productPhotoInput.getDescription());
        photo.setContentType(file.getContentType());
        photo.setSize(file.getSize());
        photo.setFileName(file.getOriginalFilename());

        ProductPhoto savedPhoto = catalogProductPhoto.save(photo, file.getInputStream());

        return productPhotoModelAssembler.toModel(savedPhoto);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long productId) {
        registrationProduct.remove(productId);
    }
}
