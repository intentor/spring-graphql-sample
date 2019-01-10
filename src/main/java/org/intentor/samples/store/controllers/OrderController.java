package org.intentor.samples.store.controllers;

import org.intentor.samples.store.domain.Order;
import org.intentor.samples.store.domain.Product;
import org.intentor.samples.store.domain.valueobjects.OrderCreationVo;
import org.intentor.samples.store.exceptions.DataNotFoundException;
import org.intentor.samples.store.repositories.OrderRepository;
import org.intentor.samples.store.repositories.ProductRepository;
import org.intentor.samples.store.repositories.UserRepository;
import org.intentor.samples.store.services.OrderService;
import org.intentor.samples.store.services.ProductService;
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
@RequestMapping(OrderController.MAIN_ROUTE)
public class OrderController {
    /**
     * Main controller route.
     */
    public static final String MAIN_ROUTE = "/api/orders";

    /**
     * Order manipulation service.
     */
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody OrderCreationVo order) {
        var entity = orderService.create(order);
        return created(URI.create("/orders/" + entity.getId())).build();
    }

    @GetMapping
    public ResponseEntity<?> findAll(
            @PageableDefault(size = MAX_PAGE_SIZE) Pageable pageable,
            @RequestParam(required = false, defaultValue = "id") String sort,
            @RequestParam(required = false, defaultValue = "asc") String order) {
        return paginate(pageable, sort, order, MAIN_ROUTE, orderService::findAll);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        return ok(orderService.findById(id));
    }
}
