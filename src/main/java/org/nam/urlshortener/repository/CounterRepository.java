package org.nam.urlshortener.repository;

import org.nam.urlshortener.entity.Counter;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CounterRepository extends MongoRepository<Counter, String> {
    Counter findCounterByClassName(String className);
}
