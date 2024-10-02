package org.nam.urlshortener.repository;

import org.nam.urlshortener.entity.Counter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

@Document(collection = "counters")
public interface CounterRepository extends MongoRepository<Counter, String> {
    Counter findCounterByClassName(String className);
}
