package org.nam.urlshortener.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "urls")
@Data
public class Url {

    private String id;

    private String longUrl;

    private String password;

    private String alias;

    private LocalDateTime createdAt;

    private LocalDate expiry;

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

    public Url(String password, String alias, LocalDate expiry) {
        ObjectId id = new ObjectId();

        this.id = id.toString();
        this.createdAt = LocalDateTime.now();
        this.password = password;
        this.alias = alias;
        this.expiry = expiry;
    }
}
