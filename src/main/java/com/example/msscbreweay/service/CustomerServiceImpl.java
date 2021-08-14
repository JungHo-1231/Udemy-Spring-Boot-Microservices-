package com.example.msscbreweay.service;

import com.example.msscbreweay.web.model.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    @Override
    public CustomerDto findCustomerById(UUID customerId) {
        return CustomerDto.builder()
                .id(customerId)
                .name("customer Test name")
                .build();
    }

    @Override
    public CustomerDto savedNewCustomer(CustomerDto customerDto) {

        return CustomerDto.builder()
                .id(customerDto.getId())
                .name(customerDto.getName())
                .build();
    }

    @Override
    public void updateCustomer(UUID beerId, CustomerDto customerDto) {
        // todo updateCustomerDto
    }

    @Override
    public void deleteById(UUID customerId) {
        log.debug("delete id...");
    }
}
