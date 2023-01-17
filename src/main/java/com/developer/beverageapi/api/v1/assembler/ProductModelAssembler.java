package com.developer.beverageapi.api.v1.assembler;

import com.developer.beverageapi.api.v1.InstantiateLinks;
import com.developer.beverageapi.api.v1.controller.ControllerRestaurantProduct;
import com.developer.beverageapi.api.v1.model.ProductModel;
import com.developer.beverageapi.domain.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProductModelAssembler extends RepresentationModelAssemblerSupport<Product, ProductModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InstantiateLinks instantiateLinks;

    public ProductModelAssembler() {
        super(ControllerRestaurantProduct.class, ProductModel.class);
    }

    @Override
    public ProductModel toModel(Product product) {
        ProductModel productModel = createModelWithId(
                product.getId(), product, product.getRestaurant().getId());

        modelMapper.map(product, productModel);

        productModel.add(instantiateLinks.linkToProducts(product.getRestaurant().getId(), "products"));

        productModel.add(instantiateLinks.linkToProductPhoto(product.getRestaurant().getId(), productModel.getId(), "photo"));

        return productModel;
    }
}
