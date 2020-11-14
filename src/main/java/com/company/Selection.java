package com.company;

import com.company.model.Offering;

import java.util.ArrayList;
import java.util.List;

public class Selection {
    private final List<Offering> selectedItems = new ArrayList<>();

    public List<Offering> getSelectedItems() {
        return selectedItems;
    }

    public void addItem(Offering item) {
        this.selectedItems.add(item);
    }
}
