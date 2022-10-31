package com.developer.beverageapi.api.controller;

import com.developer.beverageapi.api.assembler.ProductInputDismantle;
import com.developer.beverageapi.api.assembler.ProductModelAssembler;
import com.developer.beverageapi.api.model.ProductModel;
import com.developer.beverageapi.api.model.input.ProductInput;
import com.developer.beverageapi.api.model.input.ProductPhotoInput;
import com.developer.beverageapi.domain.model.Product;
import com.developer.beverageapi.domain.model.Restaurant;
import com.developer.beverageapi.domain.repository.RepositoryProduct;
import com.developer.beverageapi.domain.service.ProductRegistrationService;
import com.developer.beverageapi.domain.service.RestaurantRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PutMapping(path = "/{productId}/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updatePhoto(@PathVariable Long restaurantId, @PathVariable Long productId,
                            ProductPhotoInput productPhotoInput) {

        var fileName = UUID.randomUUID().toString() + "_" + productPhotoInput.getFile().getOriginalFilename();

        var filePhoto = Path.of("/Users/diogo/Documents", fileName);

        System.out.println(productPhotoInput.getDescription());
        System.out.println(filePhoto);
        System.out.println(productPhotoInput.getFile().getContentType());

        try {
            productPhotoInput.getFile().transferTo(filePhoto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long productId) {
        registrationProduct.remove(productId);
    }
}
