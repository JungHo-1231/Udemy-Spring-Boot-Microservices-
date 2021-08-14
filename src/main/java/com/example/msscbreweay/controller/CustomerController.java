package com.example.msscbreweay.controller;

import com.example.msscbreweay.service.CustomerService;
import com.example.msscbreweay.web.model.CustomerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable("customerId") UUID customerId) {
        return new ResponseEntity<>(customerService.findCustomerById(customerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity handlePost(@RequestBody CustomerDto customerDto) {
        CustomerDto savedNewCustomer = customerService.savedNewCustomer(customerDto);
        HttpHeaders headers = new HttpHeaders();

        // todo add hostname to url
        headers.add("Location", "/api/v1/customer" + savedNewCustomer.getId().toString());

        return ResponseEntity.created(headers.getLocation()).build();
    }

    @PutMapping({"/{beerId}"})
    public ResponseEntity handleUpdate(@PathVariable("beerId") UUID beerId,
                                       @RequestBody CustomerDto customerDto) {

        customerService.updateCustomer(beerId, customerDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping({"/{customerId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable("customerId") UUID customerId){
        customerService.deleteById(customerId);
    }
}
