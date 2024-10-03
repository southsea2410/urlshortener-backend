package org.nam.urlshortener.repository;

import org.nam.urlshortener.entity.Click;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TrafficLogsRepository extends MongoRepository<Click, String> {
}
