package org.intentor.samples.store.controllers;

import org.intentor.samples.store.domain.Product;
import org.intentor.samples.store.exceptions.DataNotFoundException;
import org.intentor.samples.store.repositories.ProductRepository;
import org.intentor.samples.store.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.intentor.samples.store.helpers.StringHelper.notEmpty;

import javax.validation.Valid;
import java.net.URI;

import static org.intentor.samples.store.Constants.MAX_PAGE_SIZE;
import static org.intentor.samples.store.controllers.helpers.PaginationHelper.paginate;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

/**
 * API for products related operations.
 */
@RestController
@RequestMapping(ProductController.MAIN_ROUTE)
public class ProductController {
    /**
     * Main controller route.
     */
    public static final String MAIN_ROUTE = "/api/products";

    /**
     * Product manipulation service.
     */
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Product product) {
        productService.create(product);
        return created(URI.create("/products/" + product.getProductSku())).build();
    }

    @GetMapping
    public ResponseEntity<?> findAll(
            @PageableDefault(size = MAX_PAGE_SIZE) Pageable pageable,
            @RequestParam(required = false, defaultValue = "id") String sort,
            @RequestParam(required = false, defaultValue = "asc") String order) {
        return paginate(pageable, sort, order, MAIN_ROUTE, productService::findAll);
    }

    @GetMapping(path = "/{sku}")
    public ResponseEntity<?> findById(@PathVariable("sku") String sku) {
        return ok(productService.findBySku(sku));
    }

    @PutMapping("/{sku}")
    public ResponseEntity<?> update(@PathVariable("sku") String sku, @RequestBody Product product) {
        product.setProductSku(sku);
        return ok(productService.update(product));
    }

    @PatchMapping("/{sku}/activate")
    public ResponseEntity<?> activate(@PathVariable("sku") String sku) {
        productService.activate(sku, true);
        return ok().build();
    }

    @PatchMapping("/{sku}/deactivate")
    public ResponseEntity<?> deactivate(@PathVariable("sku") String sku) {
        productService.activate(sku, false);
        return ok().build();
    }
}
