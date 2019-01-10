package org.intentor.samples.store.repositories;

import org.intentor.samples.store.domain.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

/**
 * Repository for processing {@link Product} data.
 */
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    /**
     * Finds a product by SKU.
     *
     * @param productSku Product SKU to look for.
     * @return User with the given username or {@literal Optional#empty()} if none found.
     */
    Optional<Product> findByProductSku(String productSku);
}