package com.couchbase.customer360.web;

import com.couchbase.customer360.data.CustomerRepository;
import com.couchbase.customer360.domain.Customer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class CustomerController {
    private final CustomerRepository repo;

    public CustomerController(CustomerRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Customer> getCustomer(@PathVariable String customerId) {
        Customer entity = repo.findById(customerId);
        if(entity == null) {
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        } else {
            URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("{customerId}").buildAndExpand(customerId).toUri();
            ResponseEntity response = ResponseEntity.ok().location(location).body(entity);
            return response;
        }
    }

    @PostMapping("/customer")
    public ResponseEntity<Void> createCustomer(@RequestBody Customer customer) {
        repo.create(customer);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{itemId}")
                .buildAndExpand(customer.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/customer/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer, @PathVariable String customerId) {
        if (! customerId.equals(customer.getType() + "::" + customer.getId())) {
            return new ResponseEntity<Customer>(HttpStatus.BAD_REQUEST);
        } else {
            repo.update(customer);
            return ResponseEntity.ok(customer);
        }
    }

    @DeleteMapping("/customer/{customerId}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable String customerId) {
        Customer customer = repo.findById(customerId);
        repo.delete(customer);
        return ResponseEntity.noContent().build();
    }
}
