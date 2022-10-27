package com.developer.beverageapi.infrastructure.service;

import com.developer.beverageapi.api.model.dto.DailySale;
import com.developer.beverageapi.domain.filter.DailySaleFilter;
import com.developer.beverageapi.domain.model.Order;
import com.developer.beverageapi.domain.service.SaleQueryService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@Repository
public class SaleQueryServiceImpl implements SaleQueryService {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<DailySale> consultDailySale(DailySaleFilter filter) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(DailySale.class);
        var root = query.from(Order.class);

        var functionCreationDate = builder.function("date", LocalDate.class, root.get("creationDate"));

        var selection = builder.construct(DailySale.class,
                functionCreationDate,
                builder.count(root.get("id")),
                builder.sum(root.get("total")));

        query.select(selection);
        query.groupBy(functionCreationDate);

        return manager.createQuery(query).getResultList();
    }
}
