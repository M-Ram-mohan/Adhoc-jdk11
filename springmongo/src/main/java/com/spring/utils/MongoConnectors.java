package com.spring.utils;

import com.mongodb.WriteConcern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.WriteResultChecking;
import org.springframework.stereotype.Service;
import pojos.Stage;

@Service
public class MongoConnectors {

    private MongoTemplate template;
    @Autowired
    public MongoConnectors(MongoTemplate mongoTemplate){
        System.out.println("Testing Spring");
        this.template = mongoTemplate;
        createDocument();
    }

    public void createDocument(){
        System.out.println(template.getCollectionNames());
        Stage stage = new Stage("MODEL_HANDLER","Model persisted", "v1", System.currentTimeMillis());
        template.setWriteResultChecking(WriteResultChecking.EXCEPTION);
        template.setWriteConcern(WriteConcern.ACKNOWLEDGED);
        try {
            template.insert(stage);
        } catch (DataAccessException exception){
            System.out.println(exception.getMessage());
            throw exception;
        }
    }
    /*
    Stage Audit collection id should be bridge node : business date : model Available At : stage : task published at
    This way insert will throw an error. And the id can be the above.
     */

}
