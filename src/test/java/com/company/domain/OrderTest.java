package com.company.domain;


import com.company.ctrl.Selection;
import com.company.dao.ProductDao;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OrderTest {

    @Test
    public void beverageAndSnackCombo_extraIsFree() {
        Product beverage = ProductDao.productMediumCoffee();
        Product snack = ProductDao.productBaconRoll();
        Product extra = beverage.getExtras().get(0);
        Order order = new Order();
        Selection selection = buildSelection(beverage, snack, extra);
        order.addSelection(selection);

        assertEquals(3, order.getOrderItems().size());
        assertEquals(beverage.getPrice() + snack.getPrice(), order.getTotalPrice(), 0.001);
    }

    @Test
    public void beverageAndSnackCombo_multipleSelections_extraIsFree() {
        Product beverage = ProductDao.productMediumCoffee();
        Product snack = ProductDao.productBaconRoll();
        Product extra = beverage.getExtras().get(0);
        Order order = new Order();

        Selection selection = buildSelection(beverage, extra);
        order.addSelection(selection);

        Selection selection2 = buildSelection(snack);
        order.addSelection(selection2);


        assertEquals(3, order.getOrderItems().size());
        assertEquals(beverage.getPrice() + snack.getPrice(), order.getTotalPrice(), 0.001);
    }


    @Test
    public void beverageAndSnackCombo_multipleSelections() {
        Order order = new Order();

        Selection selection = buildSelection(ProductDao.productOrangeJuice());
        order.addSelection(selection);

        Selection selection2 = buildSelection(ProductDao.productOrangeJuice());
        order.addSelection(selection2);

        Selection selection3 = buildSelection(ProductDao.productBaconRoll());
        order.addSelection(selection3);

        Selection selection4 = buildSelection(ProductDao.productBaconRoll());
        order.addSelection(selection4);


        assertEquals(4, order.getOrderItems().size());
        assertEquals(ProductDao.productOrangeJuice().getPrice() * 2 + ProductDao.productBaconRoll().getPrice() * 2, order.getTotalPrice(), 0.001);
    }

    @Test
    public void beverageAndSnackCombo_multipleSelections_mostExpensiveExtraFree() {
        Order order = new Order();

        Selection selection = buildSelection(ProductDao.productSmallCoffee(), ProductDao.extraMilk());
        order.addSelection(selection);

        Selection selection2 = buildSelection(ProductDao.productSmallCoffee(), ProductDao.extraSpecialRoast());
        order.addSelection(selection2);

        Selection selection3 = buildSelection(ProductDao.productBaconRoll());
        order.addSelection(selection3);

        assertEquals(5, order.getOrderItems().size());
        assertEquals(ProductDao.productSmallCoffee().getPrice() * 2 +
                ProductDao.productBaconRoll().getPrice() +
                ProductDao.extraMilk().getPrice(), order.getTotalPrice(), 0.001);
    }

    @Test
    public void beverageAndSnackCombo_multipleSelections_extraIsFree2() {
        Order order = new Order();

        Selection selection = buildSelection(ProductDao.productLargeCoffee(), ProductDao.extraMilk());
        order.addSelection(selection);

        Selection selection2 = buildSelection(ProductDao.productLargeCoffee(), ProductDao.extraSpecialRoast());
        order.addSelection(selection2);

        Selection selection3 = buildSelection(ProductDao.productBaconRoll());
        order.addSelection(selection3);

        Selection selection4 = buildSelection(ProductDao.productBaconRoll());
        order.addSelection(selection4);


        assertEquals(6, order.getOrderItems().size());
        assertEquals(ProductDao.productLargeCoffee().getPrice() * 2 + ProductDao.productBaconRoll().getPrice() * 2, order.getTotalPrice(), 0.001);
    }

    @Test
    public void beverage_freeWithStampBonus() {
        Product beverage = ProductDao.productLargeCoffee();
        Product snack = ProductDao.productBaconRoll();
        Selection selection = buildSelection(beverage, snack);
        Order order = new Order();
        order.addSelection(selection);
        order.setStampCardApplied();

        assertEquals(2, order.getOrderItems().size());
        assertEquals(snack.getPrice(), order.getTotalPrice(), 0.001);
    }

    @Test
    public void stampCard_and_comboBonus() {
        Product beverage = ProductDao.productSmallCoffee();
        Product snack = ProductDao.productBaconRoll();
        Product extra = beverage.getExtras().get(0);
        Order order = new Order();
        Selection selection = buildSelection(beverage, snack, extra);
        order.addSelection(selection);
        order.setStampCardApplied();

        assertEquals(3, order.getOrderItems().size());
        assertEquals(snack.getPrice(), order.getTotalPrice(), 0.001);
    }

    private Selection buildSelection(Product... orderItems) {
        Selection selection = new Selection();
        for (Product item : orderItems) {
            selection.addItem(item);
        }
        return selection;
    }
}