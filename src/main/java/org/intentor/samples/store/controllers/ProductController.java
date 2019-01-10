package org.intentor.samples.store.controllers;

import org.intentor.samples.store.domain.Product;
import org.intentor.samples.store.exceptions.DataNotFoundException;
import org.intentor.samples.store.repositories.ProductRepository;
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
     * Controller entity name.
     */
    public static final String ENTITY_NAME = "product";

    /**
     * Repository for accessing products data.
     */
    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Product product) {
        productRepository.save(product);
        return created(URI.create("/products/" + product.getProductSku())).build();
    }

    @GetMapping(path = "/{sku}")
    public ResponseEntity<?> findById(@PathVariable("sku") String sku) {
        return productRepository.findByProductSku(sku)
                .map(entity -> ok(entity))
                .orElseThrow(() -> new DataNotFoundException(ENTITY_NAME, sku));
    }

    @GetMapping
    public ResponseEntity<?> findAll(
            @PageableDefault(size = MAX_PAGE_SIZE) Pageable pageable,
            @RequestParam(required = false, defaultValue = "id") String sort,
            @RequestParam(required = false, defaultValue = "asc") String order) {
        return paginate(pageable, sort, order, MAIN_ROUTE, productRepository::findAll);
    }

    @PutMapping("/{sku}")
    public ResponseEntity<?> updateProduct(@PathVariable("sku") String sku, @RequestBody Product product) {
        return productRepository.findByProductSku(sku)
                .map(entity -> {
                    if (notEmpty(product.getProductName())) {
                        entity.setProductName(product.getProductName());
                    }

                    if (product.getProductPrice() > 0) {
                        entity.setProductPrice(product.getProductPrice());
                    }

                    productRepository.save(entity);

                    return ok(entity);
                })
                .orElseThrow(() -> new DataNotFoundException(ENTITY_NAME, sku));
    }

    @PatchMapping("/{sku}/activate")
    public ResponseEntity<?> activate(@PathVariable("sku") String sku) {
        return productRepository.findByProductSku(sku)
                .map(entity -> {
                    entity.setActive(true);
                    productRepository.save(entity);

                    return ok().build();
                })
                .orElseThrow(() -> new DataNotFoundException(ENTITY_NAME, sku));
    }

    @PatchMapping("/{sku}/deactivate")
    public ResponseEntity<?> deactivate(@PathVariable("sku") String sku) {
        return productRepository.findByProductSku(sku)
                .map(entity -> {
                    entity.setActive(false);
                    productRepository.save(entity);

                    return ok().build();
                })
                .orElseThrow(() -> new DataNotFoundException(ENTITY_NAME, sku));
    }
}
