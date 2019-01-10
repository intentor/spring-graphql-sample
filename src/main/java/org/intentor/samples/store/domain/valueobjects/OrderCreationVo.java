package org.intentor.samples.store.domain.valueobjects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * VO for creating an order.
 */
public class OrderCreationVo {
    @NotBlank
    private String username;

    @NotEmpty
    private List<String> productsSku;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getProductsSku() {
        return productsSku;
    }

    public void setProductsSku(List<String> productsSku) {
        this.productsSku = productsSku;
    }
}
