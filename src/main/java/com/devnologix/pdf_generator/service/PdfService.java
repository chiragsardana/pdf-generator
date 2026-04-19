package com.devnologix.pdf_generator.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.devnologix.pdf_generator.config.CompanyConfig;
import com.devnologix.pdf_generator.dto.InvoiceRequest;
import com.devnologix.pdf_generator.dto.Item;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import lombok.val;

@Service
public class PdfService {

    private final SpringTemplateEngine templateEngine;
    private final CompanyConfig companyConfig;

    public PdfService(SpringTemplateEngine templateEngine,
            CompanyConfig companyConfig) {
        this.templateEngine = templateEngine;
        this.companyConfig = companyConfig;
    }

    public byte[] generatePdf(InvoiceRequest req) throws Exception {

        String invoiceNumber = req.getInvoiceNumber();
        String invoiceDate = req.getDate();
        String invoiceCustomerName = req.getCustomerName();
        String invoiceCustomerPhone = req.getCustomerPhone();
        String invoicePaymentMethod = req.getPaymentMethod();
        String invoicePayementReference = req.getPaymentReference();
        String invoiceTotalbeforegst = req.getTotalbeforegst();
        String invoiceGstTotal = req.getGstTotal();
        System.err.println("check thi " + invoiceGstTotal);
        String invoiceTotal = req.getTotal();
        List<Item> items = req.getItems();
        Integer itemsSize = items.size();
        System.err.println(itemsSize + " Item Size ");

        Integer invoiceIndexCount = 0;
        String pageSize = req.getPageSize();
        Integer itemMaxSize = req.getItemMaxSize();
        itemMaxSize = Math.min(itemMaxSize, itemsSize);
        System.out.println(itemMaxSize);

        boolean val = false;
        boolean val1 = false;
        try {
            val = invoiceGstTotal != null && Double.parseDouble(invoiceGstTotal) > 0;
            val1 = val;
            if (val) {
                if (itemsSize < itemMaxSize - 3) {
                    val = false;
                }
            }
            // if this is true that means the gsttotal is something...
        } catch (Exception e) {
            val = false;
            val1 = false;
        }
        // Final Page consist of 3 less rows (17 - 3)

        if (pageSize.equals("A4")) {
            System.out.println("This is running");
            itemMaxSize = 48;

            // return onePagePdf(invoiceNumber, invoiceDate, invoiceCustomerName, invoiceCustomerPhone,
            //         invoicePaymentMethod, invoicePayementReference, invoiceTotalbeforegst, invoiceGstTotal,
            //         invoiceTotal, items, invoiceIndexCount);

                    Integer loopCount = (itemsSize) / itemMaxSize;
            Boolean boolCheck = itemsSize % itemMaxSize == 0;

            
            /* */
            List<byte[]> pdfmerge = new ArrayList<>();
            Integer start = 0;
            Integer end = itemMaxSize;


            Boolean lastThree = itemsSize % itemMaxSize < 4;
            if (val1 && lastThree){
                // GST SUbtotal Exists
                // itemSize for the last page need to be reduced so that new page created
                if(boolCheck){
                    boolCheck = !boolCheck;
                    end = itemMaxSize - (itemsSize % itemMaxSize) - 1;
                    itemMaxSize = end;
                }
            }


            for (int i = 0; i < loopCount; i++) {

                byte[] temppdf = onePagePdf(invoiceNumber, invoiceDate, invoiceCustomerName, invoiceCustomerPhone,
                        invoicePaymentMethod, invoicePaymentMethod,
                        (i == loopCount - 1 && boolCheck) ? invoiceTotalbeforegst : "0",
                        (i == loopCount - 1 && boolCheck) ? invoiceGstTotal : "0",
                        (i == loopCount - 1 && boolCheck) ? invoiceTotal : "Page No. " + (i + 1),
                        items.subList(start, end), invoiceIndexCount);
                start = start + itemMaxSize;
                end = end + itemMaxSize;
                end = Math.min(end, itemsSize);
                pdfmerge.add(temppdf);
                invoiceIndexCount = invoiceIndexCount + itemMaxSize;
            }
            if (!boolCheck) {
                byte[] temppdf = onePagePdf(invoiceNumber, invoiceDate, invoiceCustomerName, invoiceCustomerPhone,
                        invoicePaymentMethod, invoicePaymentMethod, invoiceTotalbeforegst, invoiceGstTotal,
                        invoiceTotal, items.subList(start, end), invoiceIndexCount);
                pdfmerge.add(temppdf);
            }
            return mergePdfs(pdfmerge);
        } else if (itemsSize <= itemMaxSize && !val) {
            System.out.println("This is running");
            return onePagePdf(invoiceNumber, invoiceDate, invoiceCustomerName, invoiceCustomerPhone,
                    invoicePaymentMethod, invoicePayementReference, invoiceTotalbeforegst, invoiceGstTotal,
                    invoiceTotal, items, invoiceIndexCount);
        } else {
            Integer loopCount = (itemsSize) / itemMaxSize;
            Boolean boolCheck = itemsSize % itemMaxSize == 0;

            
            /* */
            List<byte[]> pdfmerge = new ArrayList<>();
            Integer start = 0;
            Integer end = itemMaxSize;


            Boolean lastThree = itemsSize % itemMaxSize < 4;
            if (val1 && lastThree){
                // GST SUbtotal Exists
                // itemSize for the last page need to be reduced so that new page created
                if(boolCheck){
                    boolCheck = !boolCheck;
                    end = itemMaxSize - (itemsSize % itemMaxSize) - 1;
                    itemMaxSize = end;
                }
            }


            for (int i = 0; i < loopCount; i++) {

                byte[] temppdf = onePagePdf(invoiceNumber, invoiceDate, invoiceCustomerName, invoiceCustomerPhone,
                        invoicePaymentMethod, invoicePaymentMethod,
                        (i == loopCount - 1 && boolCheck) ? invoiceTotalbeforegst : "0",
                        (i == loopCount - 1 && boolCheck) ? invoiceGstTotal : "0",
                        (i == loopCount - 1 && boolCheck) ? invoiceTotal : "Page No. " + (i + 1),
                        items.subList(start, end), invoiceIndexCount);
                start = start + itemMaxSize;
                end = end + itemMaxSize;
                end = Math.min(end, itemsSize);
                pdfmerge.add(temppdf);
                invoiceIndexCount = invoiceIndexCount + itemMaxSize;
            }
            if (!boolCheck) {
                byte[] temppdf = onePagePdf(invoiceNumber, invoiceDate, invoiceCustomerName, invoiceCustomerPhone,
                        invoicePaymentMethod, invoicePaymentMethod, invoiceTotalbeforegst, invoiceGstTotal,
                        invoiceTotal, items.subList(start, end), invoiceIndexCount);
                pdfmerge.add(temppdf);
            }
            return mergePdfs(pdfmerge);
        }

    }

    public byte[] onePagePdf(String invoiceNumber, String invoiceDate, String invoiceCustomerName,
            String invoiceCustomerPhone, String invoicePaymentMethod,
            String invoicePaymentReference, String invoiceTotalbeforegst, String invoiceGstTotal, String invoiceTotal,
            List<Item> items, Integer invoiceIndexCount) throws Exception {
        Context context = new Context();

        // ✅ COMPANY
        context.setVariable("companyName", companyConfig.getName());
        context.setVariable("companyAddress", companyConfig.getAddress());
        context.setVariable("companyPhone", companyConfig.getPhone());
        context.setVariable("companyEmail", companyConfig.getEmail());

        // ✅ INVOICE
        context.setVariable("invoiceNumber", invoiceNumber);
        context.setVariable("date", invoiceDate);

        // ✅ CUSTOMER
        context.setVariable("customerName", invoiceCustomerName);
        context.setVariable("customerPhone", invoiceCustomerPhone);

        // ✅ PAYMENT
        context.setVariable("paymentMethod", invoicePaymentMethod);
        context.setVariable("paymentReference", invoicePaymentReference);

        // ✅ ITEMS (DIRECT)
        context.setVariable("items", items);

        // ✅ TOTALS (DIRECT FROM REQUEST)
        context.setVariable("subtotal", invoiceTotalbeforegst != null ? invoiceTotalbeforegst : 0);
        context.setVariable("gstTotal", invoiceGstTotal != null ? invoiceGstTotal : 0);
        // context.setVariable("gstTotal", "" + invoiceGstTotal);
        context.setVariable("total", "" + invoiceTotal);

        context.setVariable("indexCount", invoiceIndexCount);

        String html = templateEngine.process("invoice", context);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.withHtmlContent(html, null);

        builder.toStream(outputStream);
        builder.run();

        System.out.println("PDF size: " + outputStream.size());

        return outputStream.toByteArray();
    }

    public byte[] mergePdfs(List<byte[]> pdfList) throws Exception {

        PDFMergerUtility merger = new PDFMergerUtility();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        merger.setDestinationStream(outputStream);

        for (byte[] pdf : pdfList) {
            merger.addSource(new ByteArrayInputStream(pdf));
        }

        merger.mergeDocuments(null);

        return outputStream.toByteArray();
    }

}