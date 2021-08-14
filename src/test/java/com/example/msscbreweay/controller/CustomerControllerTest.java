package com.example.msscbreweay.controller;

import com.example.msscbreweay.service.CustomerService;
import com.example.msscbreweay.web.model.CustomerDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @MockBean
    CustomerService customerService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    CustomerDto customerDto;

    @BeforeEach
    void setUp() {
        customerDto = CustomerDto.builder()
                .id(UUID.randomUUID())
                .name("Jungho")
                .build();
    }

    @Test
    void getCustomer() throws Exception {
        mockMvc.perform(get("/api/v1/customer/customer/" + customerDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void handlePost() throws Exception {
        given(customerService.savedNewCustomer(any(CustomerDto.class))).willReturn(customerDto);

        String customerDtoToJson = objectMapper.writeValueAsString(customerDto);

        mockMvc.perform(post("/api/v1/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerDtoToJson)
                )
                .andExpect(header().exists("Location"))
                .andExpect(status().isCreated())
                .andDo(print())
        ;
    }

    @Test
    void handleUpdate() throws Exception {

        String customerDtoJson = objectMapper.writeValueAsString(customerDto);

        mockMvc.perform(put("/api/v1/customer/" + customerDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerDtoJson)
                )
                .andDo(print())
                .andExpect(status().isNoContent())
        ;
        then(customerService).should().updateCustomer(any(), any());
    }

    @Test
    void deleteCustomer() throws Exception {
        mockMvc.perform(delete("/api/v1/customer/" + customerDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent())
                .andDo(print())
        ;


    }
}