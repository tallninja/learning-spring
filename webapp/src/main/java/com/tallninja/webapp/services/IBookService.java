package com.tallninja.webapp.services;

import com.tallninja.webapp.domain.Book;

public interface IBookService {
    Iterable<Book> findAll();
}
