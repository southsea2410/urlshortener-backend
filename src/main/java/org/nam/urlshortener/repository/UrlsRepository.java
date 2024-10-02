package org.nam.urlshortener.repository;

import org.nam.urlshortener.entity.Url;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UrlsRepository extends MongoRepository<Url, String> {
    Url findUrlByAlias(String alias);

    boolean existsByAlias(String alias);

    List<Url> findUrlsByAliasIn(List<String> aliases);
}
