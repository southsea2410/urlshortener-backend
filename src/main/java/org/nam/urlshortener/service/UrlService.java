package org.nam.urlshortener.service;

import org.nam.urlshortener.entity.Url;
import org.nam.urlshortener.repository.UrlsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UrlService {
    private final UrlsRepository urlsRepository;
    private final CounterService counterService;

    @Autowired
    public UrlService(UrlsRepository urlsRepository, CounterService counterService) {
        this.urlsRepository = urlsRepository;
        this.counterService = counterService;
    }

    public Url add(Url url) {
        if (url.getAlias() == null || url.getAlias().isEmpty()) {
            long sequence = counterService.getNextSequence(this.getClass().getCanonicalName());

            String alias = Long.toString(sequence, 62);
            String paddedAlias = String.format("%7s", alias).replace(' ', '0');

            url.setAlias(paddedAlias);

            return urlsRepository.insert(url);
        }

        boolean existing = checkAlias(url.getAlias());
        if (existing) {
            throw new IllegalArgumentException("Alias already exists");
        }

        return urlsRepository.insert(url);
    }

    public boolean checkAlias(String alias) {
        return urlsRepository.existsByAlias(alias);
    }

    public Url findUrlByAlias(String alias) {
        return urlsRepository.findUrlByAlias(alias);
    }

    public Url findUrlToRedirect(String alias) {
        Url url = urlsRepository.findUrlByAlias(alias);
        if (url.getExpiry() != null && url.getExpiry().isBefore(java.time.LocalDateTime.now())) {
            throw new IllegalArgumentException("Alias expired");
        }
        return url;
    }

    public List<Url> findUrlsByAliasIn(List<String> aliases) {
        return urlsRepository.findUrlsByAliasIn(aliases);
    }
}
