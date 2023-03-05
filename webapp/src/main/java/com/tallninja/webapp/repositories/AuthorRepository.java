package com.tallninja.webapp.repositories;

import com.tallninja.webapp.domain.Author;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AuthorRepository extends CrudRepository<Author, UUID> { }
