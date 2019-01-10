package org.intentor.samples.store.services;

import org.intentor.samples.store.domain.Product;
import org.intentor.samples.store.exceptions.DataNotFoundException;
import org.intentor.samples.store.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static org.intentor.samples.store.helpers.StringHelper.notEmpty;

/**
 * Service to manage product data.
 */
@Service
public class ProductService {
    /**
     * Controller entity name.
     */
    private static final String ENTITY_NAME = "product";

    /**
     * Repository for accessing products data.
     */
    @Autowired
    private ProductRepository productRepository;

    /**
     * Creates a new product.
     *
     * @param product Product to be created.
     * @return Created product, with ID populated.
     */
    public Product create(Product product) {
        return productRepository.save(product);
    }

    /**
     * Finds all products.
     *
     * @return Products found.
     */
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    /**
     * Finds all products.
     *
     * @param pageable Pagination details.
     * @return Products found.
     */
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    /**
     * Finds a product by its SKU code.
     *
     * @param sku SKU code of the product to find.
     * @return Product found.
     * @throws DataNotFoundException in case no product is found.
     */
    public Product findBySku(String sku) {
        return productRepository.findByProductSku(sku)
                .orElseThrow(() -> new DataNotFoundException(ENTITY_NAME, sku));
    }

    /**
     * Updates a product.
     *
     * @param product Product data to be updated.
     * @return Updated product.
     * @throws DataNotFoundException in case no product is found.
     */
    public Product update(Product product) {
        var entity = productRepository.findByProductSku(product.getProductSku())
                .orElseThrow(() -> new DataNotFoundException(ENTITY_NAME, product.getProductSku()));

        if (notEmpty(product.getProductName())) {
            entity.setProductName(product.getProductName());
        }

        if (product.getProductPrice() > 0) {
            entity.setProductPrice(product.getProductPrice());
        }

        return productRepository.save(entity);
    }

    /**
     * Activates/deactivates a product.
     *
     * @param sku      SKU code of the product to activate.
     * @param activate True if the product should be activated, otherwise false.
     * @throws DataNotFoundException in case no product is found.
     */
    public void activate(String sku, boolean activate) {
        var entity = productRepository.findByProductSku(sku)
                .orElseThrow(() -> new DataNotFoundException(ENTITY_NAME, sku));

        entity.setActive(activate);
        productRepository.save(entity);
    }
}
