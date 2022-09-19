package org.example.models;

import java.time.LocalDateTime;

/***
 * This class is responsible for creating new Product objects.
 * @author dragos
 */
public class Product {

    private Integer orderId;
    private String description;
    private String gtin;
    private Double price;
    private String supplier;

    private LocalDateTime localDateTime;

    public Product() {
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getDescription() {
        return description;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public String getGtin() {
        return gtin;
    }

    public Double getPrice() {
        return price;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGtin(String gtin) {
        this.gtin = gtin;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
}
