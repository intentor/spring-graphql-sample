package org.intentor.samples.store.graphql.mutations;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.intentor.samples.store.domain.Order;
import org.intentor.samples.store.domain.valueobjects.OrderCreationVo;
import org.intentor.samples.store.domain.valueobjects.ProductSkuVo;
import org.intentor.samples.store.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * GraphQL product mutations.
 */
@Component
public class OrderMutations implements GraphQLMutationResolver {
    /**
     * Order manipulation service.
     */
    @Autowired
    private OrderService orderService;

    /**
     * Creates a new order.
     *
     * @param username Username of the user creating the order.
     * @param skus     Products SKUs associated with the order.
     * @return Created product, with ID populated.
     */
    public Order createOrder(String username, List<ProductSkuVo> skus) {
        var order = new OrderCreationVo();
        order.setUsername(username);

        var productSkus = skus.stream()
                .map(item -> item.sku)
                .collect(Collectors.toList());

        order.setProductsSku(productSkus);
        return orderService.create(order);
    }
}
