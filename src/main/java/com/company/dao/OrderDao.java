package com.company.dao;

public class OrderDao {
    private static int orderPositionId = 1;

    public static int getNextOrderPositionId() {
        return orderPositionId++;
    }
}
