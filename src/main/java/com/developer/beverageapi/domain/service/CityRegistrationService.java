package com.developer.beverageapi.domain.service;

import com.developer.beverageapi.domain.exception.CityNotFoundException;
import com.developer.beverageapi.domain.exception.EntityInUseException;
import com.developer.beverageapi.domain.model.City;
import com.developer.beverageapi.domain.model.State;
import com.developer.beverageapi.domain.repository.RepositoryCity;
import com.developer.beverageapi.domain.repository.RepositoryState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CityRegistrationService {

    public static final String MSG_CITY_BEING_USED = "City with the code %d cannot be removed," +
            "because it is being used";

    @Autowired
    private RepositoryCity repositoryCity;

    @Autowired
    private RepositoryState repositoryState;

    @Autowired
    private StateRegistrationService registrationState;

    @Transactional
    public City add(City city) {

        Long stateId = city.getState().getId();

        State state = registrationState.searchOrFail(stateId);

        city.setState(state);

        return repositoryCity.save(city);
    }

    @Transactional
    public void remove(Long cityId) {
        try {
            repositoryCity.deleteById(cityId);

        } catch (EmptyResultDataAccessException e) {
            throw new CityNotFoundException(cityId);

        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(MSG_CITY_BEING_USED, cityId));
        }
    }

    public City searchOrFail(Long cityId) {
        return repositoryCity.findById(cityId)
                .orElseThrow(() -> new CityNotFoundException(cityId));
    }
}
