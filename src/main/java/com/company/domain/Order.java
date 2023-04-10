package com.company.domain;

import com.company.ctrl.Selection;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Order {
    private final List<OrderPosition> orderItems = new LinkedList<>();
    private final List<OrderDiscount> orderDiscounts = new LinkedList<>();
    private boolean stampCardApplied;

    public List<OrderPosition> getOrderItems() {
        return orderItems;
    }

    public List<OrderDiscount> getOrderDiscounts() {
        return orderDiscounts;
    }

    public void addSelection(Selection selection) {
        List<OrderPosition> orderPositions = toOrderPositions(selection);
        orderItems.addAll(orderPositions);
        applySnackBeverageComboBonus();
    }

    public void setStampCardApplied() {
        if (!this.stampCardApplied) {
            this.stampCardApplied = true;
            applyStampCardBonus();
        } else {
            throw new IllegalStateException("stamp card bonus has already been applied to this order!");
        }
    }

    public double getTotalPrice() {
        double totalOrderPrice = orderItems.stream().mapToDouble(OrderPosition::getPrice).sum();
        double totalDiscountPrice = orderDiscounts.stream().mapToDouble(OrderDiscount::getDiscountedAmount).sum();
        return totalOrderPrice - totalDiscountPrice;
    }

    /**
     * Using bonus makes the most expensive beverage in the order free
     */
    private void applyStampCardBonus() {
        orderItems.stream()
                .filter(OrderPosition::isBeverage)
                .max(Comparator.comparing(OrderPosition::getPrice))
                .ifPresent(this::addDiscount);
    }

    private void applySnackBeverageComboBonus() {
        List<OrderPosition> beverages = this.orderItems.stream()
                .filter(OrderPosition::isBeverage)
                .collect(Collectors.toList());
        List<OrderPosition> snacks = this.orderItems.stream()
                .filter(OrderPosition::isSnack)
                .collect(Collectors.toList());
        Set<OrderPosition> discountedExtras = this.orderDiscounts.stream()
                .map(OrderDiscount::getOrderPosition)
                .filter(OrderPosition::isExtra)
                .collect(Collectors.toSet());

        int combosCount = Math.min(beverages.size(), snacks.size());
        int moreExtrasToDiscount = combosCount - discountedExtras.size();

        this.orderItems.stream()
                .filter(OrderPosition::isExtra)
                .filter(orderPosition -> !discountedExtras.contains(orderPosition))
                .sorted(Comparator.comparing(OrderPosition::getPrice).reversed())
                .limit(moreExtrasToDiscount)
                .forEach(this::addDiscount);

    }

    private void addDiscount(OrderPosition discountedPosition) {
        this.orderDiscounts.add(new OrderDiscount(discountedPosition, discountedPosition.getPrice()));
    }

    private static List<OrderPosition> toOrderPositions(Selection selection) {
        return selection.getSelectedItems().stream()
                .map(OrderPosition::fromProduct)
                .collect(Collectors.toList());
    }

}
