package com.devnologix.pdf_generator.dto;

import java.util.List;

import lombok.Data;



@Data
public class InvoiceRequest {

    private String invoiceNumber;
    private String date;

    private String customerName;
    private String customerPhone;

    private String paymentMethod;
    private String paymentReference;

    private String pageSize;
    private Integer itemMaxSize;

    // totals (from client)
    private String total;
    private String gstTotal;
    private String totalbeforegst;

    private List<Item> items;
}