package com.company.ctrl;

import com.company.dao.ProductDao;
import com.company.domain.*;

import java.io.IOException;
import java.util.*;

public class OrderController {

    private static final int RECEIPT_WIDTH = 54;
    private static final String ORDER_POSITION_HEADER = "%-30s %7s %7s %7s";
    private static final String ORDER_POSITION_LINE = "%-30s %7.2f %7.2f %7.2f";
    private final Scanner scanner = new Scanner(System.in);

    private Order order;
    private final ProductDao productDao;

    public OrderController(ProductDao productDao) {
        this.productDao = productDao;
        this.order = new Order();
    }

    public void takeNewOrder() {
        this.order = new Order();
        boolean orderComplete = false;
        while (!orderComplete) {
            addSelection();
            orderComplete = completeOrder();
        }
        applyStampCardBonus();
        printFinalReceipt();
    }

    private void addSelection() {
        clearScreen();
        Selection selection = new Selection();
        System.out.println("Choose product:\n");
        Product selectedItem = selectProduct(productDao.getProductOfferings());
        selection.addItem(selectedItem);
        if (selectedItem.hasExtras()) {
            selectExtra(selectedItem).ifPresent(selection::addItem);
        }
        selectMatchingCombo(selectedItem).ifPresent(selection::addItem);
        order.addSelection(selection);
        printOrderList();
    }


    private Product selectProduct(List<? extends Product> offerings) {
        Map<Integer, ? extends Product> orderItemMap = asMap(offerings);
        for (Map.Entry<Integer, ? extends Product> offering : orderItemMap.entrySet()) {
            System.out.printf("%d %s at %.2f CHF\n", offering.getKey(), offering.getValue().getName(),
                    offering.getValue().getPrice());
        }
        System.out.print("\nEnter option: ");
        int menuNumber = scanner.nextInt();
        return orderItemMap.get(menuNumber);
    }

    private Optional<Product> selectExtra(Product selectedItem) {
        if (needExtra()) {
            clearScreen();
            System.out.println("\nChoose an extra:\n");
            return Optional.of(selectProduct(selectedItem.getExtras()));
        }
        return Optional.empty();
    }

    private Optional<Product> selectMatchingCombo(Product selectedItem) {
        List<Product> comboOfferings;
        if (selectedItem.getProductCategory() == ProductCategory.BEVERAGE) {
            comboOfferings = productDao.getProductsByCategory().get(ProductCategory.SNACK);
        } else {
            comboOfferings = productDao.getProductsByCategory().get(ProductCategory.BEVERAGE);
        }
        clearScreen();
        System.out.println("\nAdd a matching combo? y/n");
        String answer = scanner.next();
        if (answer.equalsIgnoreCase("y")) {
            clearScreen();
            System.out.println("\nChoose a matching product:\n");
            return Optional.of(selectProduct(comboOfferings));
        }
        return Optional.empty();
    }

    private boolean needExtra() {
        System.out.print("\nAdd an extra? y/n ");
        String answer = scanner.next();
        return answer.equalsIgnoreCase("y");
    }

    private boolean completeOrder() {
        System.out.print("\nComplete order? y/n ");
        String answer = scanner.next();
        return answer.equalsIgnoreCase("y");
    }

    private void applyStampCardBonus() {
        System.out.print("\nApply 5th beverage free bonus? y/n ");
        String answer = scanner.next();
        if (answer.equalsIgnoreCase("y")) {
            order.setStampCardApplied();
        }
    }

    private void printOrderList() {
        clearScreen();
        System.out.println(formatOrderList());
    }

    private void printFinalReceipt() {
        System.out.println(formatOrderList());
        System.out.println("-".repeat(RECEIPT_WIDTH));
        System.out.printf("%-46s %7.2f", "Total", order.getTotalPrice());
    }

    private String formatOrderList() {
        StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append("Order:\n");
        outputBuilder.append(String.format(ORDER_POSITION_HEADER, "Product", "Price", "Discount", "Final"));
        for (OrderPosition orderItem : order.getOrderItems()) {
            OrderDiscount oderItemDiscount = findMatchingDiscount(orderItem);
            outputBuilder.append("\n").append(String.format(ORDER_POSITION_LINE, orderItem.getName(),
                    orderItem.getPrice(),
                    0 - oderItemDiscount.getDiscountedAmount(),
                    orderItem.getPrice() - oderItemDiscount.getDiscountedAmount()));
        }
        return outputBuilder.toString();
    }

    private OrderDiscount findMatchingDiscount(OrderPosition orderItem) {
        return order.getOrderDiscounts().stream()
                .filter(orderDiscount -> orderDiscount.getOrderPosition().equals(orderItem))
                .findFirst()
                .orElse(dummyDiscount());
    }

    private OrderDiscount dummyDiscount() {
        return new OrderDiscount(null, 0.0);
    }

    private static <T> Map<Integer, T> asMap(List<T> orderItems) {
        int seq = 1;
        Map<Integer, T> orderItemMap = new HashMap<>();
        for (T item : orderItems) {
            orderItemMap.put(seq++, item);
        }
        return orderItemMap;
    }

    private static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
