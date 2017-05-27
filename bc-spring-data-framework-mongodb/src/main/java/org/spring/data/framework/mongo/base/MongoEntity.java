package org.spring.data.framework.mongo.base;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;

import java.io.Serializable;


public class MongoEntity implements Serializable {
    @Id
    @Indexed(unique = true)
    protected String id;
    @Transient
    private int version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
