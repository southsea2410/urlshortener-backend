package org.nam.urlshortener.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.nam.urlshortener.dto.MetabasePayloadDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@CrossOrigin
public class TrafficLogsService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${metabase.secret.key}")
    private String METABASE_SECRET_KEY;

    public String getMetabaseEmbedLink(MetabasePayloadDto metabasePayloadDto) {
        String METABASE_SITE_URL = "http://metabase.haina.id.vn";

        // Create the payload
        Map<String, Object> payload = new HashMap<>();
        payload.put("resource", metabasePayloadDto.resource);
        payload.put("params", metabasePayloadDto.params);
        payload.put("exp", (System.currentTimeMillis() / 1000) + (10 * 60)); // 10 minute expiration


        try {
            log.info("Metabase payload: " + objectMapper.writeValueAsString(payload));

            // Generate the JWT token
            String token = Jwts.builder()
                    .setPayload(objectMapper.writeValueAsString(payload))
                    .signWith(Keys.hmacShaKeyFor(METABASE_SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                    .compact();

            // Create the iframe URL
            String iframeUrl = METABASE_SITE_URL + "/embed/dashboard/" + token + "#bordered=true&titled=true";

            // Print the iframe URL
            log.info("Iframe URL: " + iframeUrl);

            return iframeUrl;
        } catch (Exception e) {
            log.error("Error while generating Metabase embed link", e);
            throw new RuntimeException("Error while generating Metabase embed link");
        }
    }
}
