package org.lia.java_lab8_client_v2.tools;

import org.lia.java_lab8_client_v2.models.Product;

import java.io.Serializable;

public class ReplaceProductCommand implements Serializable {
    private static final long serialVersionUID = 1785464768755190753L;
    private Product old_product;
    private Product new_product;

    public ReplaceProductCommand(Product old_product, Product new_product) {
        this.old_product = old_product;
        this.new_product = new_product;
    }

}
