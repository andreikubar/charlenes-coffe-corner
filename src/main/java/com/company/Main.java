package com.company;

import com.company.model.ProductDao;

public class Main {

    public static void main(String[] args) {
        ProductDao productDao = new ProductDao();
        OrderController orderController = new OrderController(productDao);
        orderController.takeNewOrder();
    }

}
