package com.company.model;


import com.company.Selection;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OrderTest {

    private ProductDao productDao;

    @Before
    public void setUp() {
        productDao = new ProductDao();
    }

    @Test
    public void beverageAndSnackCombo_extraIsFreeBonus() {
        Product beverage = getRandomBeverage();
        Product snack = getRandomSnack();
        Offering extra = beverage.getExtras().get(0);
        Order order = new Order();
        Selection selection = buildSelection(beverage, snack, extra);
        order.addSelection(selection);

        assertEquals(3, order.getOrderItems().size());
        assertEquals(beverage.getPrice() + snack.getPrice(), order.getTotalPrice(), 0.001);
    }

    @Test
    public void beverageFree_withStampBonus() {
        Product beverage = getRandomBeverage();
        Product snack = getRandomSnack();
        Selection selection = buildSelection(beverage, snack);
        Order order = new Order();
        order.addSelection(selection);
        order.applyStampCardBonus();

        assertEquals(2, order.getOrderItems().size());
        assertEquals(snack.getPrice(), order.getTotalPrice(), 0.001);
    }

    @Test
    public void stampCard_and_comboBonus() {
        Product beverage = getRandomBeverage();
        Product snack = getRandomSnack();
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

    private Product getRandomBeverage() {
        return productDao.getProductsByCategory().get(OfferingCategory.BEVERAGE).get(0);
    }

    private Product getRandomSnack() {
        return productDao.getProductsByCategory().get(OfferingCategory.SNACK).get(0);
    }

}