package com.developer.beverageapi.api.v2.assembler;

import com.developer.beverageapi.api.v2.InstantiateLinksV2;
import com.developer.beverageapi.api.v2.controller.ControllerCityV2;
import com.developer.beverageapi.api.v2.model.CityModelV2;
import com.developer.beverageapi.domain.model.City;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CityModelAssemblerV2 extends RepresentationModelAssemblerSupport<City, CityModelV2> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InstantiateLinksV2 instantiateLinks;

    public CityModelAssemblerV2() {
        super(ControllerCityV2.class, CityModelV2.class);
    }

    @Override
    public CityModelV2 toModel(City city) {
        CityModelV2 cityModel = createModelWithId(city.getId(), city);

        modelMapper.map(city, cityModel);

        cityModel.add(instantiateLinks.linkToCities("cities"));

        return cityModel;
    }

    @Override
    public CollectionModel<CityModelV2> toCollectionModel(Iterable<? extends City> entities) {
        return super.toCollectionModel(entities).add(instantiateLinks.linkToCities());
    }
}
