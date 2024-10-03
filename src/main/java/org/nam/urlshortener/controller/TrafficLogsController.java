package org.nam.urlshortener.controller;

import org.nam.urlshortener.dto.MetabasePayloadDto;
import org.nam.urlshortener.service.TrafficLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrafficLogsController {

    private final TrafficLogsService trafficLogsService;

    @Autowired
    public TrafficLogsController(TrafficLogsService trafficLogsService) {
        this.trafficLogsService = trafficLogsService;
    }

    @PostMapping("/metabase_link")
    public ResponseEntity<String> getMetabaseLink(@RequestBody MetabasePayloadDto metabasePayloadDto) {
        try {
            String url = trafficLogsService.getMetabaseEmbedLink(metabasePayloadDto);
            return ResponseEntity.ok(url);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error while getting Metabase link");
        }
    }
}
