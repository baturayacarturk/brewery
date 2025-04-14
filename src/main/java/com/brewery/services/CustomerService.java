package com.brewery.services;

import com.brewery.web.model.CustomerDto;

import java.util.UUID;

public interface CustomerService {

    CustomerDto getCustomerById(UUID id);

    CustomerDto save(CustomerDto customerDto);

    void updateCustomer(CustomerDto customerDto, UUID customerId);

    void deleteById(UUID customerId);
}
