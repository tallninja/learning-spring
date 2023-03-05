package com.tallninja.webapp.repositories;

import com.tallninja.webapp.domain.Publisher;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PublisherRepository extends CrudRepository<Publisher, UUID> { }
