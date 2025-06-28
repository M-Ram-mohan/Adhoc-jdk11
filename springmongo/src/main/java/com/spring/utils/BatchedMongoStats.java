package com.spring.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class BatchedMongoStats {

    private MongoTemplate mongoTemplate;

    @Autowired
    public BatchedMongoStats(MongoTemplate mongoTemplate) {

    }
}
