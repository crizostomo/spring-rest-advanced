package com.developer.beverageapi.api.assembler;

import com.developer.beverageapi.api.InstantiateLinks;
import com.developer.beverageapi.api.controller.ControllerRestaurantProduct;
import com.developer.beverageapi.api.model.ProductPhotoModel;
import com.developer.beverageapi.domain.model.ProductPhoto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProductPhotoModelAssembler extends RepresentationModelAssemblerSupport<ProductPhoto, ProductPhotoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InstantiateLinks instantiateLinks;

    public ProductPhotoModelAssembler() {
        super(ControllerRestaurantProduct.class, ProductPhotoModel.class);
    }

    @Override
    public ProductPhotoModel toModel(ProductPhoto photo) {
        ProductPhotoModel productPhotoModel = modelMapper.map(photo, ProductPhotoModel.class);

        productPhotoModel.add(instantiateLinks.linkToProductPhoto(photo.getRestaurantId(), photo.getProduct().getId()));

        productPhotoModel.add(instantiateLinks.linkToProduct(photo.getRestaurantId(), photo.getProduct().getId(), "product"));

        return productPhotoModel;
    }
}
