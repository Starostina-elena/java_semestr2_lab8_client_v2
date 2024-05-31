package org.lia.java_lab8_client_v2.tools;

import org.lia.java_lab8_client_v2.models.Product;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class Response implements Serializable {

    private static final long serialVersionUID = 178546477190753L;

    private Product product;
    private ArrayList<String> answer = new ArrayList<>();
    private ArrayDeque<Product> productCollectionResponse;
    private boolean success;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ArrayList<String> getAnswer() {
        return answer;
    }

    public void addAnswer(String line) {
        this.answer.add(line);
    }

    public void setProductCollectionResponse(ArrayDeque<Product> productCollectionResponse) {
        this.productCollectionResponse = productCollectionResponse;
    }

    public ArrayDeque<Product> getProductCollectionResponse() {
        return productCollectionResponse;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public  boolean getSuccess() {
        return success;
    }
}
