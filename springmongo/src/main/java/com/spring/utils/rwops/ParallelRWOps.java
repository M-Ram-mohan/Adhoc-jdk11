package com.spring.utils.rwops;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.StopWatch;
import pojos.Stage;

import java.util.List;

public class ParallelRWOps implements RWOps {
    private MongoTemplate template;

    public ParallelRWOps(MongoTemplate template) {
        this.template = template;
    }

    public void test() {
        template.dropCollection(Stage.class);
        measureOps();
    }

    private void measureOps() {
        List<String> keys = getRecordKeys();
        List<Stage> stageList = getStageTestRecords();
        StopWatch watch = new StopWatch("ParallelRWOps");
        watch.start("writeOps");
        measureWrites(stageList);
        watch.stop();
        watch.start("readOps");
        measureReads(keys);
        watch.stop();
        System.out.println("Detailed report:\n" + watch.prettyPrint());
    }

    private void measureReads(List<String> keys ) {
        keys.parallelStream().forEach(key -> template.find(Query.query(Criteria.where("key").is(key)), Stage.class));
    }

    private void measureWrites(List<Stage> stageList) {
        stageList.parallelStream().forEach(stage -> {
            template.save(stage);
        });
    }
}
