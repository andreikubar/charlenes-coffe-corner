package com.company.model;

import com.company.model.coffeeextras.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductDao {

    private final List<Product> offerings = new ArrayList<>();
    private final Map<OfferingCategory, List<Product>> productsByCategory;

    public ProductDao() {
        buildOfferings();
        productsByCategory = offerings.stream().collect(Collectors.groupingBy(Offering::getOfferingCategory));
    }

    public List<Product> getProductOfferings() {
        return offerings;
    }

    public Map<OfferingCategory, List<Product>> getProductsByCategory() {
        return productsByCategory;
    }

    private void buildOfferings() {
        addCoffeeOffering();
        addBaconRollOffering();
        addOrangeJuiceOffering();
    }

    private void addCoffeeOffering() {
        List<ProductExtra> coffeeExtras = List.of(
                new ExtraMilkExtra(),
                new FoamedMilkExtra(),
                new SpecialRoastCoffeeExtra());

        Product smallCoffee = new Product.Builder("Small Coffee")
                .withProductCategory(OfferingCategory.BEVERAGE)
                .withPrice(2.5)
                .withExtras(coffeeExtras)
                .build();
        offerings.add(smallCoffee);

        Product mediumCoffee = new Product.Builder("Medium Coffee")
                .withProductCategory(OfferingCategory.BEVERAGE)
                .withPrice(3.0)
                .withExtras(coffeeExtras)
                .build();
        offerings.add(mediumCoffee);

        Product largeCoffee = new Product.Builder("Large Coffee")
                .withProductCategory(OfferingCategory.BEVERAGE)
                .withPrice(3.5)
                .withExtras(coffeeExtras)
                .build();
        offerings.add(largeCoffee);
    }

    private void addBaconRollOffering() {
        Product baconRoll = new Product.Builder("Bacon Roll")
                .withProductCategory(OfferingCategory.SNACK)
                .withPrice(4.5)
                .build();
        offerings.add(baconRoll);
    }

    private void addOrangeJuiceOffering() {
        Product orangeJuice = new Product.Builder("Freshly squeezed orange juice")
                .withProductCategory(OfferingCategory.BEVERAGE)
                .withPrice(3.95)
                .build();
        offerings.add(orangeJuice);
    }
}
