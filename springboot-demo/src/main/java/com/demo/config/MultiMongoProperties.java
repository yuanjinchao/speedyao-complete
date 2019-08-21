package com.demo.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "sss")
public class MultiMongoProperties {

    private MongoProperties primary=new MongoProperties();
    private MongoProperties secondary=new MongoProperties();
}

