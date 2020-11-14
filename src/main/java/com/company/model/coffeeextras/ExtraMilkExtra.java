package com.company.model.coffeeextras;

import com.company.model.ProductExtra;

public class ExtraMilkExtra extends ProductExtra {
    @Override
    public Double getPrice() {
        return 0.3;
    }

    @Override
    public String getName() {
        return "Extra milk";
    }

}
