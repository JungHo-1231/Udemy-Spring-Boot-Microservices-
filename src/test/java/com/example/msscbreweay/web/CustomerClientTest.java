package com.example.msscbreweay.web;

import com.example.msscbreweay.web.model.CustomerDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerClientTest {

    @Autowired
    CustomerClient client;

    @Test
    void testGetCustomerById() throws Exception {
        UUID uuid = UUID.randomUUID();
        CustomerDto customerById = client.getCustomerById(uuid);
        assertEquals(customerById.getId(), uuid);
    }

    @Test
    void testSaveCustomerDto() throws Exception {
        CustomerDto customerDto = CustomerDto.builder()
                .id(UUID.randomUUID())
                .name("jung")
                .build();

        URI uri = client.saveNewCustomer(customerDto);
        assertThat(uri.toString()).isEqualTo("/api/v1/customer"+customerDto.getId());
    }

    @Test
    void testUpdateCustomerDto() throws Exception{
        CustomerDto customerDto = CustomerDto.builder()
                .id(UUID.randomUUID())
                .name("jung")
                .build();
        client.updateCustomer(UUID.randomUUID(), customerDto);
    }

    @Test
    void testDeleteCustomer() throws Exception{
        client.deleteCustomer(UUID.randomUUID());
    }

}