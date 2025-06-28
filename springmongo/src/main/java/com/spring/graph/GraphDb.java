package com.spring.graph;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import pojos.Stage;

@Component
public class GraphDb {

    private final MongoTemplate template;

    @Autowired
    public GraphDb(MongoTemplate mongoTemplate){
        this.template = mongoTemplate;
    }



}

/*
TODO :
1. Use profiling to see if the queries are getting any faster

 */