package com.example.bookorder.repositories;

import com.example.bookorder.models.entities.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, String> {
}
