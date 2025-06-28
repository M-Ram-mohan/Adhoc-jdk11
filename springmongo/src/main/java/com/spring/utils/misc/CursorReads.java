package com.spring.utils.misc;

import com.mongodb.client.MongoCursor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.util.CloseableIterator;
import pojos.Stage;

import java.util.ArrayList;
import java.util.List;

public class CursorReads implements DbRequest{

    public MongoTemplate mongoTemplate;
    private int count, pageSize;
    public CursorReads(MongoTemplate template) {
        this.mongoTemplate = template;
    }

    @Override
    public void execute(int COUNT, int BATCH_SIZE){
        this.count = COUNT;
        this.pageSize = BATCH_SIZE;
        Query query = Query.query(Criteria.where("modelId").is("123"));
        List<Stage> batch = new ArrayList<>();
        try(CloseableIterator<Stage> cursor = mongoTemplate.stream(query, Stage.class)) {
            while(cursor.hasNext()) {
                batch.add(cursor.next());
            }
        }
    }
}
