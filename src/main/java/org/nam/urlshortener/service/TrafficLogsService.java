package org.nam.urlshortener.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.nam.urlshortener.dto.MetabasePayloadDto;
import org.nam.urlshortener.entity.Click;
import org.nam.urlshortener.repository.TrafficLogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@CrossOrigin
public class TrafficLogsService {
    private final TrafficLogsRepository trafficLogsRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${metabase.secret.key}")
    private String METABASE_SECRET_KEY;

    @Autowired
    public TrafficLogsService(TrafficLogsRepository trafficLogsRepository) {
        this.trafficLogsRepository = trafficLogsRepository;
    }

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

    public void recordTrafficLog(HttpServletRequest request) {

        String country = request.getHeader("CF-IPCountry");
        String alias = request.getRequestURI().replace("/", "");
        String userAgent = request.getHeader("User-Agent");
        String referer = request.getHeader("Referer");
        String ip = request.getRemoteAddr();
        LocalDateTime time = LocalDateTime.now();

        Enumeration<String> headers = request.getHeaderNames();

        while (headers.hasMoreElements()) {
            String header = headers.nextElement();
            log.info(header + ": " + request.getHeader(header));
        }

        Click click = new Click(country, alias, referer, userAgent, time, ip);
        trafficLogsRepository.save(click);
    }
}
