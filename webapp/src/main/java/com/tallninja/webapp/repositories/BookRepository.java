package com.tallninja.webapp.repositories;

import com.tallninja.webapp.domain.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface BookRepository extends CrudRepository<Book, UUID> { }
