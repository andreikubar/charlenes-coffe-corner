package com.company.model.coffeeextras;

import com.company.model.ProductExtra;

public class SpecialRoastCoffeeExtra extends ProductExtra {
    @Override
    public Double getPrice() {
        return 0.9;
    }

    @Override
    public String getName() {
        return "Special roast coffee";
    }

}
