package com.developer.beverageapi.domain.service;

import com.developer.beverageapi.domain.exception.EntityInUseException;
import com.developer.beverageapi.domain.exception.EntityNotFoundException;
import com.developer.beverageapi.domain.model.City;
import com.developer.beverageapi.domain.model.State;
import com.developer.beverageapi.domain.repository.RepositoryCity;
import com.developer.beverageapi.domain.repository.RepositoryState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CityRegistrationService {

    @Autowired
    private RepositoryCity repositoryCity;

    @Autowired
    private RepositoryState repositoryState;

    public City add(City city){

        Long stateId = city.getState().getId();
        Optional<State> state = repositoryState.findById(stateId);

        if (state.isEmpty()){
            throw new EntityNotFoundException(
                    String.format("There is no state registry with code %d", stateId));
        }

        city.setState(state.get());

        return repositoryCity.save(city);
    }

    public void remove(Long cityId){
        try {
            repositoryCity.deleteById(cityId);

        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(
                    String.format("There is no City with the code %d",
                            cityId));

        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format("City with the code %d cannot be removed," +
                            "because it is being used", cityId));
        }
    }
}
