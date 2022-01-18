package com.example.bookorder.models.forms;

import lombok.Data;

@Data
public class StatisticsForm {
    private String month;
    private int totalOrderCount;
    private int totalBookCount;
    private double totalPurchasedAmount;
}
