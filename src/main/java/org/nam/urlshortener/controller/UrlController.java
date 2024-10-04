package org.nam.urlshortener.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.nam.urlshortener.dto.AliasList;
import org.nam.urlshortener.entity.Url;
import org.nam.urlshortener.service.TrafficLogsService;
import org.nam.urlshortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin
public class UrlController {

    private final TrafficLogsService trafficLogsService;

    private final UrlService urlService;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Autowired
    public UrlController(UrlService urlService, TrafficLogsService trafficLogsService) {
        this.urlService = urlService;
        this.trafficLogsService = trafficLogsService;
    }

    @GetMapping("/{alias}")
    public RedirectView redirect(@PathVariable String alias, HttpServletRequest request) {
        try {
            log.info("Redirecting to alias: " + alias);
            Url url = urlService.findUrlToRedirect(alias);
            RedirectView redirectView = new RedirectView();

            if (url.getPassword() != null && !url.getPassword().isEmpty()) {
                redirectView.setUrl(frontendUrl + "password_protected?alias=" + alias);
            } else {
                redirectView.setUrl(url.getLongUrl());
                trafficLogsService.recordTrafficLog(request);
            }

            return redirectView;

        } catch (Exception e) {
            log.error("Error while redirecting", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Redirect error", e);
        }
    }

    @PostMapping(value = "/{alias}", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public RedirectView redirectWithPassword(@PathVariable String alias, @RequestParam MultiValueMap<String, String> formData, HttpServletRequest request) {
        try {
            log.info("Redirecting with password to alias: " + alias);
            Url url = urlService.findUrlToRedirect(alias);
            RedirectView redirectView = new RedirectView();

            if (url.getPassword() != null && !url.getPassword().isEmpty()) {
                String password = formData.getFirst("password");

                if (password == null || !password.equals(url.getPassword())) {
                    redirectView.setUrl(frontendUrl + "password_protected?alias=" + alias);
                } else {
                    redirectView.setUrl(url.getLongUrl());
                    trafficLogsService.recordTrafficLog(request);
                }
            } else {
                redirectView.setUrl(url.getLongUrl());
                trafficLogsService.recordTrafficLog(request);
            }


            return redirectView;

        } catch (Exception e) {
            log.error("Error while redirecting", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Redirect error", e);
        }
    }


    @PostMapping("/urls/mine")
    public ResponseEntity<List<Url>> getUrlsByAliases(@RequestBody AliasList aliasList) {
        try {
            List<Url> urls = urlService.findUrlsByAliasIn(aliasList.getAliases());
            return ResponseEntity.ok(urls);
        } catch (Exception e) {
            log.error("Error while getting URLs by aliases", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while getting URLs by aliases");
        }
    }

    @PostMapping("/urls/all")
    public ResponseEntity<List<Url>> getAllUrls() {
        try {
            List<Url> urls = urlService.findAll();
            return ResponseEntity.ok(urls);
        } catch (Exception e) {
            log.error("Error while getting all URLs", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while getting all URLs");
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
