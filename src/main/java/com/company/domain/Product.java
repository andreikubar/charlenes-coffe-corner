package com.company.domain;

import java.util.ArrayList;
import java.util.List;

public class Product {

    private final Double price;
    private final String name;
    private final List<Product> extras;
    private final ProductCategory productCategory;

    private Product(Builder builder) {
        this.name = builder.name;
        this.price = builder.price;
        this.extras = builder.extras;
        this.productCategory = builder.productCategory;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public boolean hasExtras() {
        return this.extras != null && this.extras.size() > 0;
    }

    public List<Product> getExtras() {
        return this.extras;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public static class Builder {
        private final String name;
        private Double price;
        private List<Product> extras;
        private ProductCategory productCategory;

        public Builder(String name) {
            this.name = name;
        }

        public Builder withPrice(Double price) {
            this.price = price;
            return this;
        }

        public Builder withExtras(List<Product> extras) {
            if (this.extras == null) {
                this.extras = new ArrayList<>();
            }
            this.extras.addAll(extras);
            return this;
        }

        public Builder withProductCategory(ProductCategory productCategory) {
            this.productCategory = productCategory;
            return this;
        }

        public Product build() {
            return new Product(this);
        }

    }

}
