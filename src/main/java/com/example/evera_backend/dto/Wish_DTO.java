package com.example.evera_backend.dto;

import com.example.evera_backend.entity.Product;

import java.io.Serializable;

public class Wish_DTO implements Serializable {
    private Product product;


    public Wish_DTO() {
    }

    /**
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * @param product the product to set
     */
    public void setProduct(Product product) {
        this.product = product;
    }
}