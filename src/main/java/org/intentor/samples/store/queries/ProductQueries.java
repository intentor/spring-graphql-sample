package org.intentor.samples.store.queries;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.intentor.samples.store.domain.Product;
import org.intentor.samples.store.exceptions.DataNotFoundException;
import org.intentor.samples.store.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * GraphQL product queries.
 */
@Component
public class ProductQueries implements GraphQLQueryResolver {
    /**
     * Repository for accessing users data.
     */
    @Autowired
    private ProductRepository productRepository;

    public Iterable<Product> allProducts() {
        return productRepository.findAll();
    }

    public Product findProduct(String sku) {
        return productRepository.findByProductSku(sku)
                .orElseThrow(() -> new DataNotFoundException("product", sku));
    }
}
