package com.company.model.coffeeextras;

import com.company.model.ProductExtra;

public class FoamedMilkExtra extends ProductExtra {
    @Override
    public Double getPrice() {
        return 0.5;
    }

    @Override
    public String getName() {
        return "Foamed milk";
    }

}
