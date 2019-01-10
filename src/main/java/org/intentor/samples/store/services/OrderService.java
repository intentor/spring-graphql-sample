package org.intentor.samples.store.services;

import org.intentor.samples.store.domain.Order;
import org.intentor.samples.store.domain.Product;
import org.intentor.samples.store.domain.User;
import org.intentor.samples.store.domain.valueobjects.OrderCreationVo;
import org.intentor.samples.store.exceptions.DataNotFoundException;
import org.intentor.samples.store.repositories.OrderRepository;
import org.intentor.samples.store.repositories.ProductRepository;
import org.intentor.samples.store.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Service to manage order data.
 */
@Service
public class OrderService {
    /**
     * Controller entity name.
     */
    public static final String ENTITY_NAME = "order";

    /**
     * Repository for accessing users data.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Repository for accessing products data.
     */
    @Autowired
    private ProductRepository productRepository;

    /**
     * Repository for accessing orders data.
     */
    @Autowired
    private OrderRepository orderRepository;

    /**
     * Creates a new order.
     *
     * @param order Order to be created.
     * @return Created order, with ID populated.
     */
    public Order create(OrderCreationVo order) {
        var user = userRepository.findByUsername(order.getUsername())
                .orElseThrow(() -> new DataNotFoundException("user", order.getUsername()));
        var products = new ArrayList<Product>();
        var orderTotal = 0.0;

        for (var sku : order.getProductsSku()) {
            var product = productRepository.findByProductSku(sku)
                    .orElseThrow(() -> new DataNotFoundException("product", sku));

            orderTotal += product.getProductPrice();
            products.add(product);
        }

        var entity = new Order();
        entity.setUser(user);
        entity.setProducts(products);
        entity.setOrderTotal(orderTotal);

        return orderRepository.save(entity);
    }

    /**
     * Finds all orders.
     *
     * @return Orders found.
     */
    public Iterable<Order> findAll() {
        return orderRepository.findAll();
    }

    /**
     * Finds all orders.
     *
     * @param pageable Pagination details.
     * @return Orders found.
     */
    public Page<Order> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    /**
     * Finds a order by its ID.
     *
     * @param id Order ID.
     * @return Order found.
     * @throws DataNotFoundException in case no order is found.
     */
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(ENTITY_NAME, id.toString()));
    }
}
