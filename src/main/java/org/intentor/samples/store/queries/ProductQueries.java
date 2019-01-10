package org.intentor.samples.store.queries;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.intentor.samples.store.domain.Product;
import org.intentor.samples.store.exceptions.DataNotFoundException;
import org.intentor.samples.store.repositories.ProductRepository;
import org.intentor.samples.store.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * GraphQL product queries.
 */
@Component
public class ProductQueries implements GraphQLQueryResolver {
    /**
     * Product manipulation service.
     */
    @Autowired
    private ProductService productService;

    /**
     * Finds all products.
     *
     * @return Products found.
     */
    public Iterable<Product> allProducts() {
        return productService.findAll();
    }

    /**
     * Finds a product by its SKU code.
     *
     * @param sku SKU code of the product to find.
     * @return Product found.
     */
    public Product findProduct(String sku) {
        return productService.findBySku(sku);
    }
}
