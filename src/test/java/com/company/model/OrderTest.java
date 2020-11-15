package com.company.model;


import com.company.Selection;
import com.company.model.coffeeextras.FoamedMilkExtra;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderTest {
    
    @Test
    public void beverageAndSnackCombo_extraIsFreeBonus() {
        Product beverage = createBeverageWithAnExtra();
        Product snack = createSnack();
        Offering extra = beverage.getExtras().get(0);
        Order order = new Order();
        Selection selection = buildSelection(beverage, snack, extra);
        order.addSelection(selection);

        assertEquals(3, order.getOrderItems().size());
        assertEquals(beverage.getPrice() + snack.getPrice(), order.getTotalPrice(), 0.001);
    }

    @Test
    public void beverageFree_withStampBonus() {
        Product beverage = createBeverageWithAnExtra();
        Product snack = createSnack();
        Selection selection = buildSelection(beverage, snack);
        Order order = new Order();
        order.addSelection(selection);
        order.applyStampCardBonus();

        assertEquals(2, order.getOrderItems().size());
        assertEquals(snack.getPrice(), order.getTotalPrice(), 0.001);
    }

    @Test
    public void stampCard_and_comboBonus() {
        Product beverage = createBeverageWithAnExtra();
        Product snack = createSnack();
        Offering extra = beverage.getExtras().get(0);
        Order order = new Order();
        Selection selection = buildSelection(beverage, snack, extra);
        order.addSelection(selection);
        order.applyStampCardBonus();

        assertEquals(3, order.getOrderItems().size());
        assertEquals(snack.getPrice(), order.getTotalPrice(), 0.001);
    }

    private Selection buildSelection(Offering... orderItems) {
        Selection selection = new Selection();
        for (Offering item : orderItems) {
            selection.addItem(item);
        }
        return selection;
    }

    private Product createBeverageWithAnExtra() {
        return new Product.Builder("Test beverage 1")
                .withExtras(List.of(new FoamedMilkExtra()))
                .withPrice(2.0)
                .withProductCategory(OfferingCategory.BEVERAGE)
                .build();
    }

    private Product createSnack() {
        return new Product.Builder("Test snak 1")
                .withPrice(3.0)
                .withProductCategory(OfferingCategory.SNACK)
                .build();
    }

}