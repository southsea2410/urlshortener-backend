package org.nam.urlshortener.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "traffic_logs")
public class Click {

    @Id
    public String id;

    public String alias;

    public String country;

    public String referrer;

    public String userAgent;

    public LocalDateTime clickTime;

    public String ip;

    public Click(String country, String alias, String referrer, String userAgent, LocalDateTime clickTime, String ip) {
        this.country = country;
        this.alias = alias;
        this.referrer = referrer;
        this.userAgent = userAgent;
        this.clickTime = clickTime;
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "Click{" +
                "id='" + id + '\'' +
                ", alias='" + alias + '\'' +
                ", country='" + country + '\'' +
                ", referrer='" + referrer + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", clickTime=" + clickTime +
                ", ip='" + ip + '\'' +
                '}';
    }

}
