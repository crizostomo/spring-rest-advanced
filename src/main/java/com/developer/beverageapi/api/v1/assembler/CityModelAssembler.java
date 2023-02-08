package com.developer.beverageapi.api.v1.assembler;

import com.developer.beverageapi.api.v1.InstantiateLinks;
import com.developer.beverageapi.api.v1.controller.ControllerCity;
import com.developer.beverageapi.api.v1.model.CityModel;
import com.developer.beverageapi.core.security.Security;
import com.developer.beverageapi.domain.model.City;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CityModelAssembler extends RepresentationModelAssemblerSupport<City, CityModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InstantiateLinks instantiateLinks;

    @Autowired
    private Security security;

    public CityModelAssembler() {
        super(ControllerCity.class, CityModel.class);
    }

    @Override
    public CityModel toModel(City city) {
        CityModel cityModel = createModelWithId(city.getId(), city);

        modelMapper.map(city, cityModel);

        if (security.allowedToConsultCities()) {
            cityModel.add(instantiateLinks.linkToCities("cities"));
        }

        if (security.allowedToConsultStates()) {
            cityModel.getState().add(instantiateLinks.linkToState(cityModel.getState().getId()));
        }

        return cityModel;
    }

    @Override
    public CollectionModel<CityModel> toCollectionModel(Iterable<? extends City> entities) {
        CollectionModel<CityModel> collectionModel = super.toCollectionModel(entities);

        if (security.allowedToConsultCities()) {
            collectionModel.add(instantiateLinks.linkToCities());
        }

        return  collectionModel;
    }

    //    public List<CityModel> toCollectionModel(List<City> cities) {
//        return cities.stream()
//                .map(city -> toModel(city))
//                .collect(Collectors.toList());
//    }
}
