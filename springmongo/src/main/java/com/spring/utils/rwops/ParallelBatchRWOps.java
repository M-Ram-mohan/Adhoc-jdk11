package com.spring.utils.rwops;

import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.StopWatch;
import pojos.Stage;

import java.util.ArrayList;
import java.util.List;

public class ParallelBatchRWOps implements RWOps {

    private final MongoTemplate template;

    private int BATCH_SIZE = RWOps.BATCH_SIZE;
    private int COUNT = RWOps.COUNT;
    private String comments = "ParallelBatchRWOps";

    public ParallelBatchRWOps(MongoTemplate template) {
        this.template = template;
    }

    public ParallelBatchRWOps(MongoTemplate mongoTemplate, int batchSize, int count, String comments) {
        this.template = mongoTemplate;
        BATCH_SIZE = batchSize;
        COUNT = count;
        this.comments = comments;
    }

    public void test() {
        template.dropCollection(Stage.class);
        measureOps();
    }

    private void measureOps() {
        List<String> keys = getRecordKeys(COUNT);
        List<Stage> stageList = getStageTestRecords(COUNT);
        StopWatch watch = new StopWatch(comments);
        watch.start("writeOps");
        measureWrites(stageList);
        watch.stop();
        watch.start("readOps");
        measureReads(keys);
        watch.stop();
        System.out.println("Detailed report:\n" + watch.prettyPrint());
    }

    private void measureReads(List<String> ids) {
        int batchSize = BATCH_SIZE;
        List<List<String>> batches = new ArrayList<>();
        for (int i = 0; i < ids.size(); i += batchSize) {
            List<String> batch = ids.subList(i, Math.min(i + batchSize, ids.size()));
            batches.add(batch);
        }
        batches.parallelStream().forEach(batch -> {
            Query query = new Query(Criteria.where("_id").in(batch));
            template.find(query, Stage.class);
        });
    }

    private void measureWrites(List<Stage> stageList) {
        int batchSize = BATCH_SIZE;
        List<List<Stage>> batches = new ArrayList<>();
        for (int i = 0; i < stageList.size(); i += batchSize) {
            List<Stage> batch = stageList.subList(i, Math.min(i + batchSize, stageList.size()));
            batches.add(batch);
        }
        batches.parallelStream().forEach(batch -> template.bulkOps(BulkOperations.BulkMode.UNORDERED, Stage.class).insert(batch).execute());
    }
}
