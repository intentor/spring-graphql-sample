package org.intentor.samples.store.domain.valueobjects;

import graphql.schema.GraphQLInputType;

import javax.validation.constraints.NotEmpty;

/**
 * VO for representing a product SKU code.
 * <p>
 * It's used as input on GraphQL migrations.
 */
public class ProductSkuVo implements GraphQLInputType {
    @NotEmpty
    public String sku;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return "ProductSkuInput";
    }
}
