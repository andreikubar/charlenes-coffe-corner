package com.company.dao;

import com.company.domain.Product;
import com.company.domain.ProductCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductDao {

    private final List<Product> offerings = new ArrayList<>();

    public ProductDao() {
        buildOfferings();
    }

    public List<Product> getProductOfferings() {
        return offerings;
    }

    public Map<ProductCategory, List<Product>> getProductsByCategory() {
        return offerings.stream().collect(Collectors.groupingBy(Product::getProductCategory));
    }

    private void buildOfferings() {
        offerings.add(productSmallCoffee());
        offerings.add(productMediumCoffee());
        offerings.add(productLargeCoffee());
        offerings.add(productBaconRoll());
        offerings.add(productOrangeJuice());
    }

    public static Product productSmallCoffee() {
        return new Product.Builder("Small Coffee")
                .withProductCategory(ProductCategory.BEVERAGE)
                .withPrice(2.5)
                .withExtras(getCoffeeExtraProducts())
                .build();
    }

    public static Product productMediumCoffee() {
        return new Product.Builder("Medium Coffee")
                    .withProductCategory(ProductCategory.BEVERAGE)
                    .withPrice(3.0)
                    .withExtras(getCoffeeExtraProducts())
                    .build();
    }

    public static Product productLargeCoffee() {
        return new Product.Builder("Large Coffee")
                .withProductCategory(ProductCategory.BEVERAGE)
                .withPrice(3.5)
                .withExtras(getCoffeeExtraProducts())
                .build();
    }

    public static Product productBaconRoll() {
        return new Product.Builder("Bacon Roll")
                    .withProductCategory(ProductCategory.SNACK)
                    .withPrice(4.5)
                    .build();
    }

    public static Product productOrangeJuice() {
        return new Product.Builder("Freshly squeezed orange juice")
                .withProductCategory(ProductCategory.BEVERAGE)
                .withPrice(3.95)
                .build();
    }

    public static List<Product> getCoffeeExtraProducts() {
        return List.of(
                extraMilk(),
                extraFoamedMilk(),
                extraSpecialRoast()
        );
    }

    public static Product extraMilk() {
        return new Product.Builder("Extra milk").withProductCategory(ProductCategory.EXTRA).withPrice(0.3).build();
    }

    public static Product extraFoamedMilk() {
        return new Product.Builder("Foamed milk").withProductCategory(ProductCategory.EXTRA).withPrice(0.5).build();
    }

    public static Product extraSpecialRoast() {
        return new Product.Builder("Special roast").withProductCategory(ProductCategory.EXTRA).withPrice(0.9).build();
    }

}
