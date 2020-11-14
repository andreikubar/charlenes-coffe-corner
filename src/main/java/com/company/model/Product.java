package com.company.model;

import java.util.ArrayList;
import java.util.List;

public class Product implements Offering {

    private final Double price;
    private final String name;
    private final List<ProductExtra> extras;
    private final OfferingCategory offeringCategory;

    private Product(Builder builder) {
        this.name = builder.name;
        this.price = builder.price;
        this.extras = builder.extras;
        this.offeringCategory = builder.offeringCategory;
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

    public List<ProductExtra> getExtras() {
        return this.extras;
    }

    @Override
    public OfferingCategory getOfferingCategory() {
        return offeringCategory;
    }

    public static class Builder {
        private final String name;
        private Double price;
        private List<ProductExtra> extras;
        private OfferingCategory offeringCategory;

        public Builder(String name) {
            this.name = name;
        }

        public Builder withPrice(Double price) {
            this.price = price;
            return this;
        }

        public Builder withExtras(List<ProductExtra> extras) {
            if (this.extras == null) {
                this.extras = new ArrayList<>();
            }
            this.extras.addAll(extras);
            return this;
        }

        public Builder withProductCategory(OfferingCategory offeringCategory) {
            this.offeringCategory = offeringCategory;
            return this;
        }

        public Product build() {
            return new Product(this);
        }

    }

}
