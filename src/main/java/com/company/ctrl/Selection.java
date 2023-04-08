package com.company.ctrl;

import com.company.domain.Product;

import java.util.ArrayList;
import java.util.List;

public class Selection {
    private final List<Product> selectedItems = new ArrayList<>();

    public List<Product> getSelectedItems() {
        return selectedItems;
    }

    public void addItem(Product item) {
        if (item != null) {
            this.selectedItems.add(item);
        }
    }
}
