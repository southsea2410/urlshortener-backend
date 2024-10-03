package org.nam.urlshortener.controller;

import lombok.extern.slf4j.Slf4j;
import org.nam.urlshortener.dto.AliasList;
import org.nam.urlshortener.entity.Url;
import org.nam.urlshortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin(origins = "*")
public class UrlController {

    private final UrlService urlService;

    @Autowired
    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/{alias}")
    public RedirectView redirect(@PathVariable String alias) {
        try {
            Url url = urlService.findUrlToRedirect(alias);
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl(url.getLongUrl());
            return redirectView;
        } catch (Exception e) {
            log.error("Error while redirecting", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Redirect error", e);
        }
    }

    @PostMapping("/urls")
    public ResponseEntity<List<Url>> getUrlsByAliases(@RequestBody AliasList aliasList) {
        try {
            List<Url> urls = urlService.findUrlsByAliasIn(aliasList.getAliases());
            return ResponseEntity.ok(urls);
        } catch (Exception e) {
            log.error("Error while getting URLs by aliases", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while getting URLs by aliases");
        }
    }

    @PostMapping("/shorten")
    public ResponseEntity<String> shorten(@RequestBody Url url) {
        try {
            Url newUrl = urlService.add(url);
            return ResponseEntity.ok().body(newUrl.getAlias());
        } catch (Exception e) {
            log.error("Error while shortening URL", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while shortening URL");
        }
    }
}
