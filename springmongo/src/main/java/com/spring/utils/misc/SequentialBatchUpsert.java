package com.spring.utils.misc;

import com.mongodb.MongoSocketException;
import com.mongodb.MongoTimeoutException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import pojos.Stage;

import java.util.ArrayList;
import java.util.List;

public class SequentialBatchUpsert implements DbRequest{

    public MongoTemplate template;
    private int count, batchSize;
    private List<Stage> stageList;

    public SequentialBatchUpsert(MongoTemplate template, List<Stage> stageList) {
        this.template = template;
        this.stageList = stageList;
    }

    public void execute(int COUNT, int BATCH_SIZE){
        this.count = COUNT;
        this.batchSize = BATCH_SIZE;
        for (int i = 0; i < stageList.size(); i += batchSize) {
            int end = Math.min(i + batchSize, stageList.size());
            List<Stage> batch = stageList.subList(i, end);

            BulkOperations bulkOps = template.bulkOps(BulkOperations.BulkMode.UNORDERED, Stage.class);

            for (Stage stage : batch) {
                Query query = Query.query(Criteria.where("_id").is(stage.getKey()));
                Update update = new Update()
                        .set("modelId", stage.getModelId())
                        .set("comments", stage.getComments())
                        .set("stage", stage.getStage())
                        .set("counter", stage.getCounter());
                bulkOps.upsert(query, update);
            }
            try {
                bulkOps.execute();
            } catch (DataAccessException ex) {
                if(ex.getCause() instanceof MongoTimeoutException){
                    System.out.println("Timed out waiting for a connection from the pool");
                } else if(ex.getCause() instanceof MongoSocketException) {
                    System.out.println("Mongo Socket Exception : " + ex.getCause().getMessage());
                } else {
                    System.out.println("Spring Mongo Exception : " + ex.getCause().getMessage());
                }
            } catch (Exception ex) {
                System.out.println("Exception occurred : " + ex.getMessage());
            }
        }
    }
}
