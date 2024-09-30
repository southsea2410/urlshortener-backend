package org.nam.urlshortener.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "urls")
public class Url {

    @Id
    public String id;

    public String password;

    public String alias;

    @CreatedDate
    public LocalDateTime createdAt;

    public LocalDateTime expiry;

    @Override
    public String toString() {
        return "Url{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", alias='" + alias + '\'' +
                ", createdAt=" + createdAt +
                ", expiry=" + expiry +
                '}';
    }

    public Url(String password, String alias, LocalDateTime createdAt, LocalDateTime expiry) {
        this.password = password;
        this.alias = alias;
        this.createdAt = createdAt;
        this.expiry = expiry;
    }
}
