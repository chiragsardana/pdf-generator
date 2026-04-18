package com.devnologix.pdf_generator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.Getter;

@Component
@Getter
public class CompanyConfig {

    @Value("${company.name}")
    private String name;

    @Value("${company.address}")
    private String address;

    @Value("${company.phone}")
    private String phone;

    @Value("${company.email}")
    private String email;
}