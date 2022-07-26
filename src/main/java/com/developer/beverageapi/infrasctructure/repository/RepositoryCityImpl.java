package com.developer.beverageapi.infrasctructure.repository;

import com.developer.beverageapi.domain.model.City;
import com.developer.beverageapi.domain.repository.RepositoryCity;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository //This class translates some exceptions
public class RepositoryCityImpl implements RepositoryCity {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<City> listAll(){
        return manager.createQuery("from City", City.class)
                .getResultList();
    }

    @Override
    public City searchById(Long id){
        return manager.find(City.class, id);
    }

    @Override
    @Transactional
    public City add(City city){
        return manager.merge(city);
    }

    @Override
    @Transactional
    public void remove(Long id){
        City city = searchById(id);

        if(city == null){
            throw new EmptyResultDataAccessException(1);
        }

        manager.remove(city);
    }
}
