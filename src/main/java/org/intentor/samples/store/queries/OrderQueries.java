package org.intentor.samples.store.queries;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.intentor.samples.store.domain.Order;
import org.intentor.samples.store.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * GraphQL order queries.
 */
@Component
public class OrderQueries implements GraphQLQueryResolver {
    /**
     * Order manipulation service.
     */
    @Autowired
    private OrderService orderService;

    /**
     * Finds all orders.
     *
     * @return Orders found.
     */
    public Iterable<Order> allOrders() {
        return orderService.findAll();
    }

    /**
     * Finds a order by its ID.
     *
     * @param id Order ID.
     * @return Order found.
     */
    public Order findOrder(Long id) {
        return orderService.findById(id);
    }
}
