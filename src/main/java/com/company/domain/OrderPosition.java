package com.company.domain;

import java.util.Objects;

public class OrderPosition  {
    private final int id;
    private final Double price;
    private final String name;
    private final ProductCategory productCategory;

    public OrderPosition(int id, Double price, String name, ProductCategory productCategory) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.productCategory = productCategory;
    }

    public int getId() {
        return id;
    }

    public Double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public boolean isBeverage() {
        return this.productCategory == ProductCategory.BEVERAGE;
    }

    public boolean isSnack() {
        return this.productCategory == ProductCategory.SNACK;
    }

    public boolean isExtra() {
        return this.productCategory == ProductCategory.EXTRA;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderPosition that = (OrderPosition) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
