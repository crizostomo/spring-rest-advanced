package com.developer.beverageapi.domain.service;

import com.developer.beverageapi.domain.exception.EntityInUseException;
import com.developer.beverageapi.domain.exception.KitchenNotFoundException;
import com.developer.beverageapi.domain.model.Kitchen;
import com.developer.beverageapi.domain.repository.RepositoryKitchen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KitchenRegistrationService {

    public static final String MSG_KITCHEN_BEING_USED
            = "Kitchen with the code %d cannot be removed, because it is being used";

    @Autowired
    private RepositoryKitchen repositoryKitchen;

    @Transactional
    public Kitchen add(Kitchen kitchen) {
        return repositoryKitchen.save(kitchen);
    }

    @Transactional
    public void remove(Long kitchenId) {
        try {
            repositoryKitchen.deleteById(kitchenId);
            repositoryKitchen.flush();

        } catch (EmptyResultDataAccessException e) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
//                    String.format("There is no Kitchen with the code %d", kitchenId));
            throw new KitchenNotFoundException(kitchenId);

        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(MSG_KITCHEN_BEING_USED, kitchenId));
        }
    }

    public Kitchen searchOrFail(Long kitchenId) {
        return repositoryKitchen.findById(kitchenId)
                .orElseThrow(() -> new KitchenNotFoundException(kitchenId));
    }
}
