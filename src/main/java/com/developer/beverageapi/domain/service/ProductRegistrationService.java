package com.developer.beverageapi.domain.service;

import com.developer.beverageapi.domain.exception.EntityInUseException;
import com.developer.beverageapi.domain.exception.ProductNotFoundException;
import com.developer.beverageapi.domain.model.Product;
import com.developer.beverageapi.domain.repository.RepositoryProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductRegistrationService {

    public static final String MSG_PRODUCT_BEING_USED = "Product with the code %d cannot be found" +
            "because it is being used";

    @Autowired
    private RepositoryProduct repositoryProduct;

    @Transactional
    public Product add(Product product){
        return repositoryProduct.save(product);
    }

    @Transactional
    public void remove(Long productId){
        try{
            repositoryProduct.deleteById(productId);
            repositoryProduct.flush();

        } catch (EmptyResultDataAccessException e){
            throw new ProductNotFoundException(productId);

        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(MSG_PRODUCT_BEING_USED, productId));
        }
    }

    public Product searchOrFail(Long restaurantId, Long productId) {
        return repositoryProduct.findById(restaurantId, productId)
                .orElseThrow(() -> new ProductNotFoundException(restaurantId, productId));
    }
}
