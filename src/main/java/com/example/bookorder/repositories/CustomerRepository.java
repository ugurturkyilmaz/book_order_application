package com.example.bookorder.repositories;

import com.example.bookorder.models.entities.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CustomerRepository extends MongoRepository<Customer, String> {

    @Query(value = "{'$or':[ {'email':?0} , {'phoneNumber':?1} ] }")
    Customer findCustomerByEmailOrPhoneNumber(String email, String phoneNumber);

}
