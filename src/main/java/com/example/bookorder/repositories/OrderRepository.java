package com.example.bookorder.repositories;

import com.example.bookorder.models.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {

    @Query(value = "{'customer.id':?0}")
    List<Order> findByCustomerId(String customerId);

    @Query("{'orderDate': {$gte: ?0, $lte:?1 }}")
    Page<Order> findBetweenDates(Date startDate, Date endDate, Pageable pageable);
}
