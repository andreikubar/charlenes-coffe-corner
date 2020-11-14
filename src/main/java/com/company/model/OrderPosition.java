package com.company.model;

public class OrderPosition  {
    private Double price;
    private final String name;
    private final OfferingCategory offeringCategory;

    public OrderPosition(Double price, String name, OfferingCategory offeringCategory) {
        this.price = price;
        this.name = name;
        this.offeringCategory = offeringCategory;
    }

    public Double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public void resetPrice() {
        this.price = 0.0;
    }

    public boolean isBeverage() {
        return this.offeringCategory == OfferingCategory.BEVERAGE;
    }

    public boolean isSnack() {
        return this.offeringCategory == OfferingCategory.SNACK;
    }

    public boolean isExtra() {
        return this.offeringCategory == OfferingCategory.EXTRA;
    }


}
