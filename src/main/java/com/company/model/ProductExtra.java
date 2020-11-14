package com.company.model;

public abstract class ProductExtra implements Offering {

    @Override
    public OfferingCategory getOfferingCategory() {
        return OfferingCategory.EXTRA;
    }

}
