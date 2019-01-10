package org.intentor.samples.store.controllers;

import org.intentor.samples.store.domain.Order;
import org.intentor.samples.store.domain.Product;
import org.intentor.samples.store.domain.valueobjects.OrderCreationVo;
import org.intentor.samples.store.exceptions.DataNotFoundException;
import org.intentor.samples.store.repositories.OrderRepository;
import org.intentor.samples.store.repositories.ProductRepository;
import org.intentor.samples.store.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;

import static org.intentor.samples.store.Constants.MAX_PAGE_SIZE;
import static org.intentor.samples.store.controllers.helpers.PaginationHelper.paginate;
import static org.springframework.http.ResponseEntity.*;

/**
 * API for orders related operations.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    /**
     * Main controller route.
     */
    public static final String MAIN_ROUTE = "/api/orders";
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

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody OrderCreationVo order) {
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

        orderRepository.save(entity);
        return created(URI.create("/orders/" + entity.getId())).build();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        return orderRepository.findById(id)
                .map(entity -> ok(entity))
                .orElseThrow(() -> new DataNotFoundException(ENTITY_NAME, id.toString()));
    }

    @GetMapping
    public ResponseEntity<?> findAll(
            @PageableDefault(size = MAX_PAGE_SIZE) Pageable pageable,
            @RequestParam(required = false, defaultValue = "id") String sort,
            @RequestParam(required = false, defaultValue = "asc") String order) {
        return paginate(pageable, sort, order, MAIN_ROUTE, orderRepository::findAll);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return orderRepository.findById(id)
                .map(entity -> {
                    orderRepository.delete(entity);
                    return noContent().build();
                })
                .orElseThrow(() -> new DataNotFoundException(ENTITY_NAME, id.toString()));
    }
}
