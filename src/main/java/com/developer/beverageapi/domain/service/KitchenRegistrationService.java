package com.developer.beverageapi.domain.service;

import com.developer.beverageapi.domain.exception.EntityInUseException;
import com.developer.beverageapi.domain.exception.EntityNotFoundException;
import com.developer.beverageapi.domain.model.Kitchen;
import com.developer.beverageapi.domain.repository.RepositoryKitchen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class KitchenRegistrationService {

    @Autowired
    RepositoryKitchen repositoryKitchen;

    public Kitchen add(Kitchen kitchen) {
        return repositoryKitchen.add(kitchen);
    }

    public void remove(Long kitchenId) {
        try {
            repositoryKitchen.remove(kitchenId);

        } catch (EmptyResultDataAccessException e){
            throw new EntityNotFoundException(
                    String.format("There is no Kitchen with the code %d",
                            kitchenId));

        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format("Kitchen with the code %d cannot be removed," +
                            "because it is being used", kitchenId));
        }
    }
}
