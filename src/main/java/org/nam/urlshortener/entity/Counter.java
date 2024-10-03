package org.nam.urlshortener.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "counters")
public class Counter {

    @Id
    private String id;
    private String className;
    private long sequence;

    public Counter() {
        this.id = new ObjectId().toString();
    }
}
