package com.company.model;

import com.company.Selection;

import java.util.*;
import java.util.stream.Collectors;

public class Order {
    private final List<OrderPosition> orderItems = new ArrayList<>();

    public List<OrderPosition> getOrderItems() {
        return orderItems;
    }

    public void addSelection(Selection selection) {
        List<OrderPosition> orderPositions = toOrderPositions(selection);
        orderItems.addAll(orderPositions);
        applySnackBeverageComboBonus(orderPositions);
    }

    private void applySnackBeverageComboBonus(List<OrderPosition> orderPositions) {
        if (containsBeverageAndSnack(orderPositions)) {
            makeOneExtraFree(orderPositions);
        }
    }

    private void makeOneExtraFree(List<OrderPosition> orderPositions) {
        orderPositions.stream()
                .filter(OrderPosition::isExtra)
                .max(Comparator.comparing(OrderPosition::getPrice))
                .ifPresent(OrderPosition::resetPrice);
    }

    private boolean containsBeverageAndSnack(List<OrderPosition> orderItems) {
        boolean containsBeverage = orderItems.stream().anyMatch(OrderPosition::isBeverage);
        boolean containsSnack = orderItems.stream().anyMatch(OrderPosition::isSnack);
        return containsBeverage && containsSnack;
    }

    /**
     * Using bonus makes the most expensive beverage in the order free
     */
    public void applyStampCardBonus() {
        orderItems.stream()
                .filter(OrderPosition::isBeverage)
                .max(Comparator.comparing(OrderPosition::getPrice))
                .ifPresent(OrderPosition::resetPrice);
    }

    public double getTotalPrice() {
        return orderItems.stream().mapToDouble(OrderPosition::getPrice).sum();
    }

    private static List<OrderPosition> toOrderPositions(Selection selection) {
        return selection.getSelectedItems().stream()
                .map(Order::toOrderPosition).collect(Collectors.toList());
    }

    private static OrderPosition toOrderPosition(Offering selectedItem) {
        return new OrderPosition(selectedItem.getPrice(), selectedItem.getName(), selectedItem.getOfferingCategory());
    }

}
