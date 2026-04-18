package com.devnologix.pdf_generator.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devnologix.pdf_generator.dto.InvoiceRequest;
import com.devnologix.pdf_generator.service.PdfService;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {
 
    private final PdfService pdfService;

    public InvoiceController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @PostMapping("/generate")
    public ResponseEntity<byte[]> generate(@RequestBody InvoiceRequest request) throws Exception {

        byte[] pdf = pdfService.generatePdf(request);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}