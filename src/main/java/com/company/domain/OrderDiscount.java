package com.company.domain;

public class OrderDiscount {
    private final OrderPosition orderPosition;
    private final Double discountedAmount;

    public OrderDiscount(OrderPosition orderPosition, Double discountedAmount) {
        this.orderPosition = orderPosition;
        this.discountedAmount = discountedAmount;
    }

    public OrderPosition getOrderPosition() {
        return orderPosition;
    }

    public Double getDiscountedAmount() {
        return discountedAmount;
    }
}
