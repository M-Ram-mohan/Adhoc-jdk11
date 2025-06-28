package com.spring.stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class StageDb {

    private final MongoTemplate template;

    @Autowired
    public StageDb(MongoTemplate mongoTemplate) {
        this.template = mongoTemplate;
    }

}
