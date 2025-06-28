package com.spring.utils.rwops;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.StopWatch;
import pojos.Stage;

import java.util.List;

public class SequentialRWOps implements RWOps {

    private MongoTemplate template;

    public SequentialRWOps(MongoTemplate template) {
        this.template = template;
    }

    public void test() {
        template.dropCollection(Stage.class);
        measureOps();
    }

    private void measureOps() {
        List<Stage> stageList = getStageTestRecords();
        StopWatch watch = new StopWatch("SequentialRWOps");
        watch.start("writeOps");
        measureWrites(stageList);
        watch.stop();
        watch.start("readOps");
        measureReads();
        watch.stop();
        System.out.println("Detailed report:\n" + watch.prettyPrint());
    }

    private void measureReads() {
        for (int i = 0; i < COUNT; i++) {
            String key = String.valueOf(i);
            List<Stage> stage = template.find(Query.query(Criteria.where("key").is(key)), Stage.class);
        }
    }

    private void measureWrites(List<Stage> stageList) {
        for (Stage stage : stageList) {
            template.save(stage);
        }
    }

}
