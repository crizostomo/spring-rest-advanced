package com.developer.beverageapi.api.v1.controller;

import com.developer.beverageapi.api.v1.InstantiateLinks;
import com.developer.beverageapi.api.v1.assembler.ProductInputDismantle;
import com.developer.beverageapi.api.v1.assembler.ProductModelAssembler;
import com.developer.beverageapi.api.v1.assembler.ProductPhotoModelAssembler;
import com.developer.beverageapi.api.v1.model.ProductModel;
import com.developer.beverageapi.api.v1.model.ProductPhotoModel;
import com.developer.beverageapi.api.v1.model.input.ProductInput;
import com.developer.beverageapi.api.v1.model.input.ProductPhotoInput;
import com.developer.beverageapi.api.v1.swaggerapi.controller.ControllerRestaurantProductOpenApi;
import com.developer.beverageapi.core.security.CheckSecurity;
import com.developer.beverageapi.domain.exception.EntityNotFoundException;
import com.developer.beverageapi.domain.model.Product;
import com.developer.beverageapi.domain.model.ProductPhoto;
import com.developer.beverageapi.domain.model.Restaurant;
import com.developer.beverageapi.domain.repository.RepositoryProduct;
import com.developer.beverageapi.domain.service.CatalogProductPhotoService;
import com.developer.beverageapi.domain.service.PhotoStorageService;
import com.developer.beverageapi.domain.service.ProductRegistrationService;
import com.developer.beverageapi.domain.service.RestaurantRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/restaurants/{restaurantId}/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ControllerRestaurantProduct implements ControllerRestaurantProductOpenApi {

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

    @Autowired
    private PhotoStorageService photoStorageService;

    @Autowired
    private InstantiateLinks instantiateLinks;

    @CheckSecurity.Restaurants.AllowedToConsult
    @Override
    @GetMapping
    public CollectionModel<ProductModel> list(@PathVariable Long restaurantId,
                                              @RequestParam(required = false, defaultValue = "false") Boolean includeNotActives) {
        Restaurant restaurant = registrationRestaurant.searchOrFail(restaurantId);

        List<Product> allProducts = null;

        if (includeNotActives) {
            allProducts = repositoryProduct.findProductsByRestaurant(restaurant);
        } else {
            allProducts = repositoryProduct.findActivesByRestaurant(restaurant);
        }

        return productModelAssembler.toCollectionModel(allProducts).add(instantiateLinks.linkToProducts(restaurantId));
    }

//    @GetMapping("/active-inactive")
//    public List<ProductModel> listActiveAndOrInactive(@PathVariable Long restaurantId,
//                                                      @RequestParam(required = false) boolean includeInactive) {
//        Restaurant restaurant = registrationRestaurant.searchOrFail(restaurantId);
//
//        List<Product> allProducts = null;
//
//        if (includeInactive) {
//            allProducts = repositoryProduct.findProductsByRestaurant(restaurant);
//        } else {
//            allProducts = repositoryProduct.findActivesByRestaurant(restaurant);
//        }
//
//        return productModelAssembler.toCollectionModel(allProducts);
//    }

    @CheckSecurity.Restaurants.AllowedToConsult
    @GetMapping("/{productId}")
    public ProductModel search(@PathVariable Long restaurantId, @PathVariable Long productId) {
        Product product = registrationProduct.searchOrFail(restaurantId, productId);

        return productModelAssembler.toModel(product);
    }

    @CheckSecurity.Restaurants.AllowedToManageOperation
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

    @CheckSecurity.Restaurants.AllowedToManageOperation
    @PutMapping("/{productId}")
    public ProductModel update(@PathVariable Long restaurantId, @PathVariable Long productId,
                               @RequestBody @Valid ProductInput productInput) {
        Product currentProduct = registrationProduct.searchOrFail(restaurantId, productId);

        productInputDismantle.copyToDomainObject(productInput, currentProduct);

        currentProduct = registrationProduct.add(currentProduct);

        return productModelAssembler.toModel(currentProduct);
    }

    @CheckSecurity.Restaurants.AllowedToManageOperation
    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long productId) {
        registrationProduct.remove(productId);
    }

    @CheckSecurity.Restaurants.AllowedToConsult
    @Override()
    @GetMapping(path = "/{productId}/photo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductPhotoModel searchPhoto(@PathVariable Long restaurantId, @PathVariable Long productId) {
        ProductPhoto productPhoto = catalogProductPhoto.searchOrFail(restaurantId, productId);

        return productPhotoModelAssembler.toModel(productPhoto);
    }

    @CheckSecurity.Restaurants.AllowedToConsult
    @Override()
    @GetMapping(path = "/{productId}/photo", produces = MediaType.ALL_VALUE)
    public ResponseEntity<?> getFile(@PathVariable Long restaurantId, @PathVariable Long productId,
                                                       @RequestHeader(name = "accept") String acceptHeader)
            throws HttpMediaTypeNotAcceptableException {
        try {
            ProductPhoto productPhoto = catalogProductPhoto.searchOrFail(restaurantId, productId);

            MediaType mediaTypePhoto = MediaType.parseMediaType(productPhoto.getContentType());
            List<MediaType> mediaTypesAccepted = MediaType.parseMediaTypes(acceptHeader);

            verifyCompatibilityMediaType(mediaTypePhoto, mediaTypesAccepted);

//            InputStream inputStream = photoStorageService.recover(productPhoto.getFileName());

            PhotoStorageService.PhotoRetrieved photoRetrieved = photoStorageService.recover(productPhoto.getFileName());

            if (photoRetrieved.isUrlPresent()) {
                return ResponseEntity
                        .status(HttpStatus.FOUND)
                        .header(HttpHeaders.LOCATION, photoRetrieved.getUrl())
                        .build();
            } else {
                return ResponseEntity.ok()
                        .contentType(mediaTypePhoto)
                        .body(new InputStreamResource(photoRetrieved.getInputStream()));
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private void verifyCompatibilityMediaType(MediaType mediaTypePhoto, List<MediaType> mediaTypesAccepted)
            throws HttpMediaTypeNotAcceptableException {

        boolean compatible = mediaTypesAccepted.stream()
                .anyMatch(mediaType -> mediaType.isCompatibleWith(mediaTypePhoto));

        if (!compatible) {
            throw new HttpMediaTypeNotAcceptableException(mediaTypesAccepted);
        }
    }

    @CheckSecurity.Restaurants.AllowedToManageOperation
    @Override()
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

    @CheckSecurity.Restaurants.AllowedToManageOperation
    @Override()
    @DeleteMapping(path = "/{productId}/photo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePhoto(@PathVariable Long restaurantId, @PathVariable Long productId) {
        catalogProductPhoto.delete(restaurantId, productId);
    }
}
