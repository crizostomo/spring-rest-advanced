package com.developer.beverageapi.infrastructure.service.query;

import com.developer.beverageapi.api.v1.model.dto.DailySale;
import com.developer.beverageapi.domain.filter.DailySaleFilter;
import com.developer.beverageapi.domain.model.Order;
import com.developer.beverageapi.domain.model.OrderStatus;
import com.developer.beverageapi.domain.service.SaleQueryService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class SaleQueryServiceImpl implements SaleQueryService {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<DailySale> consultDailySale(DailySaleFilter filter, String timeOffset) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(DailySale.class);
        var root = query.from(Order.class);
        var predicates = new ArrayList<Predicate>();

        var functionConvertTzCreationDate = builder.function(
                "convert_tz",
                Date.class,
                root.get("creationDate"),
                builder.literal("+00:00"),
                builder.literal(timeOffset));

        var functionCreationDate = builder.function("date", Date.class, functionConvertTzCreationDate);

        var selection = builder.construct(DailySale.class,
                functionCreationDate,
                builder.count(root.get("id")),
                builder.sum(root.get("total")));

        if (filter.getRestaurantId() != null) {
            predicates.add(builder.equal(root.get("restaurant"), filter.getRestaurantId()));
        }

        if (filter.getCreationDateStart() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("creationDate"),
                    filter.getCreationDateStart()));
        }

        if (filter.getCreationDateEnd() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("creationDate"),
                    filter.getCreationDateEnd()));
        }

        predicates.add(root.get("status").in(OrderStatus.CONFIRMED, OrderStatus.DELIVERED));

        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));
        query.groupBy(functionCreationDate);

        return manager.createQuery(query).getResultList();
    }
}
