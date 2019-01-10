package org.intentor.samples.store.graphql.mutations;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.intentor.samples.store.domain.Product;
import org.intentor.samples.store.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * GraphQL product mutations.
 */
@Component
public class ProductMutations implements GraphQLMutationResolver {
    /**
     * Product manipulation service.
     */
    @Autowired
    private ProductService productService;

    /**
     * Creates a new product.
     *
     * @param sku   SKU code of the new product.
     * @param name  Name of the new product.
     * @param price Price of the new product.
     * @return Created product, with ID populated.
     */
    public Product createProduct(String sku, String name, Double price) {
        var product = new Product();
        product.setProductSku(sku);
        product.setProductName(name);
        product.setProductPrice(price);
        product.setActive(true);
        return productService.create(product);
    }

    /**
     * Updates a product.
     *
     * @param sku   SKU code of the product to be updated
     * @param name  New product's name.
     * @param price New product's price.
     * @return Updated product.
     */
    public Product updateProduct(String sku, String name, Double price) {
        var product = new Product();
        product.setProductSku(sku);
        product.setProductName(name);
        product.setProductPrice(price);
        return productService.update(product);
    }

    /**
     * Activates a product.
     *
     * @param sku SKU code of the product to be activated.
     */
    public boolean activateProduct(String sku) {
        productService.activate(sku, true);
        return true;
    }

    /**
     * Deactivates a product.
     *
     * @param sku SKU code of the product to be deactivated.
     */
    public boolean deactivateProduct(String sku) {
        productService.activate(sku, false);
        return true;
    }
}
