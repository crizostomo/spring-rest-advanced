package com.developer.beverageapi.api.assembler;

import com.developer.beverageapi.api.model.ProductPhotoModel;
import com.developer.beverageapi.domain.model.ProductPhoto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductPhotoModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public ProductPhotoModel toModel(ProductPhoto photo) {

        return modelMapper.map(photo, ProductPhotoModel.class);
    }
}
