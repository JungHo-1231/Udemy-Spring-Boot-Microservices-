package com.example.msscbreweay.service;

import com.example.msscbreweay.web.model.CustomerDto;

import java.util.UUID;

public interface CustomerService {
    CustomerDto findCustomerById(UUID customerId);

    CustomerDto savedNewCustomer(CustomerDto customerDto);

    void updateCustomer(UUID beerId, CustomerDto customerDto);

    void deleteById(UUID customerId);
}
