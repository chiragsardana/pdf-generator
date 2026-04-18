package com.devnologix.pdf_generator.dto;


import lombok.Data;

@Data
public class Item {

    private String name;
    private String itemId;

    private int qty;
    private String qtyDetails;

    private double price;
    private double discount;
    private double gstRate;

    // client provided
    private double priceAndGstRate;
    private double amount;
}