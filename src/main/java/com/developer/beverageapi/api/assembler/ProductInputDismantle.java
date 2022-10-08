package com.developer.beverageapi.api.assembler;

import com.developer.beverageapi.api.model.input.ProductInput;
import com.developer.beverageapi.domain.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductInputDismantle {

    @Autowired
    private ModelMapper modelMapper;

    public Product toDomainObject(ProductInput productInput) {

        return modelMapper.map(productInput, Product.class);
    }

    public void copyToDomainObject(ProductInput productInput, Product product) {
        modelMapper.map(productInput, product);
    }
}
