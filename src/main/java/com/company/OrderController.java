package com.company;

import com.company.model.*;

import java.io.IOException;
import java.util.*;

public class OrderController {

    private static final int RECEIPT_WIDTH = 40;
    private static final String ORDER_POSITION_LINE = "%-30s %.2f CHF";
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
        Offering addedItem = addProduct(selection, productDao.getProductOfferings());
        addMatchingCombo(selection, addedItem);
        order.addSelection(selection);
        printOrderList();
    }

    private Offering addProduct(Selection selection, List<Product> offerings) {
        Offering selectedItem = selectOrderItem(offerings);
        selection.addItem(selectedItem);
        addExtras(selectedItem, selection);
        return selectedItem;
    }

    private void addExtras(Offering selectedItem, Selection selection) {
        if (selectedItem instanceof Product) {
            Product selectedProduct = (Product) selectedItem;
            if (selectedProduct.hasExtras()) {
                if (addExtra()) {
                    clearScreen();
                    System.out.println("Choose extra:\n");
                    Offering selectedExtra = selectOrderItem(selectedProduct.getExtras());
                    selection.addItem(selectedExtra);
                }
            }
        }
    }

    private void addMatchingCombo(Selection selection, Offering selectedItem) {
        List<Product> comboOfferings;
        if (selectedItem.getOfferingCategory() == OfferingCategory.BEVERAGE) {
            comboOfferings = productDao.getProductsByCategory().get(OfferingCategory.SNACK);
        } else {
            comboOfferings = productDao.getProductsByCategory().get(OfferingCategory.BEVERAGE);
        }
        clearScreen();
        System.out.println("\nAdd a matching combo? y/n");
        String answer = scanner.next();
        if (answer.equalsIgnoreCase("y")) {
            clearScreen();
            System.out.println("Choose a matching product:\n");
            addProduct(selection, comboOfferings);
        }
    }

    private Offering selectOrderItem(List<? extends Offering> offerings) {
        Map<Integer, ? extends Offering> orderItemMap = asMap(offerings);
        for (Map.Entry<Integer, ? extends Offering> offering : orderItemMap.entrySet()) {
            System.out.printf("%d %s at %.2f CHF\n", offering.getKey(), offering.getValue().getName(),
                    offering.getValue().getPrice());
        }
        System.out.print("\nEnter option: ");
        int menuNumber = scanner.nextInt();
        return orderItemMap.get(menuNumber);
    }

    private boolean addExtra() {
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
            order.applyStampCardBonus();
        }
    }

    private void printOrderList() {
        clearScreen();
        System.out.println(formatOrderList());
    }

    private void printFinalReceipt() {
        System.out.println(formatOrderList());
        System.out.println("-".repeat(RECEIPT_WIDTH));
        System.out.printf(ORDER_POSITION_LINE, "Total", order.getTotalPrice());
    }

    private String formatOrderList() {
        StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append("Order:\n");
        outputBuilder.append("-".repeat(RECEIPT_WIDTH));
        for (OrderPosition orderItem : order.getOrderItems()) {
            outputBuilder.append("\n").append(String.format(ORDER_POSITION_LINE, orderItem.getName(),
                    orderItem.getPrice()));
        }
        return outputBuilder.toString();
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
