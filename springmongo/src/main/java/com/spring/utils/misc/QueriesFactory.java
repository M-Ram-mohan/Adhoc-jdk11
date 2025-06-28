package com.spring.utils.misc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import pojos.Stage;

import java.util.ArrayList;
import java.util.List;

@Service
public class QueriesFactory implements DbRequest {

    MongoTemplate template;

    private Logger LOGGER = LoggerFactory.getLogger(QueriesFactory.class);

    @Autowired
    public QueriesFactory(MongoTemplate template) {
        this.template = template;
        this.execute(1000, 100);
        this.execute(1000, 500);
        this.execute(10000, 2000);
        this.execute(10000, 5000);
        this.execute(20000, 2000);
        this.execute(20000, 5000);
        this.execute(50000, 2000);
        this.execute(50000, 5000);
    }

    public void execute(int COUNT, int BATCH_SIZE) {
        template.dropCollection(Stage.class);
        List<Stage> stageList = getStageRecords(0, COUNT, "123");
        List<Stage> stageList1 = getStageRecords(0, COUNT, "456");
        List<Stage> stageList2 = getStageRecords(0, COUNT, "789");
        StopWatch watch = new StopWatch("DbQueries");
        watch.start("Parallel Batch Insert : " + COUNT + " with Batch : " + BATCH_SIZE);
        loadRecordsToMongo(COUNT, BATCH_SIZE, stageList);
        watch.stop();
        watch.start("Paginated Reads");
        DbRequest pgReads = new PaginatedReads(template);
        pgReads.execute(COUNT, BATCH_SIZE);
        watch.stop();
        watch.start("Cursor Reads");
        DbRequest cursorReads = new CursorReads(template);
        cursorReads.execute(COUNT, BATCH_SIZE);
        watch.stop();
        watch.start("Sequential Batch Upsert");
        DbRequest sequentialBatchUpsert = new SequentialBatchUpsert(template, stageList1);
        sequentialBatchUpsert.execute(COUNT, BATCH_SIZE);
        watch.stop();
        watch.start("Parallel Batch Upsert");
        DbRequest parallelBatchUpsert = new ParallelBatchUpsert(template, stageList2);
        parallelBatchUpsert.execute(COUNT, BATCH_SIZE);
        watch.stop();
        System.out.println(watch.prettyPrint());
    }

    public void loadRecordsToMongo(int COUNT, int BATCH_SIZE, List<Stage> stageList) {
        int batchSize = BATCH_SIZE;
        List<List<Stage>> batches = new ArrayList<>();
        for (int i = 0; i < stageList.size(); i += batchSize) {
            List<Stage> batch = stageList.subList(i, Math.min(i + batchSize, stageList.size()));
            batches.add(batch);
        }
        batches.parallelStream().forEach(batch -> {
            int retry = 0;
            while(retry < 3) {
                try{
                    template.bulkOps(BulkOperations.BulkMode.UNORDERED, Stage.class).insert(batch).execute();
                    break;
                } catch (DataAccessException ex) {
                    retry++;
                    LOGGER.error("Exception while inserting the data : {}", ex.getMessage());
                }
            }
        });
    }

    List<Stage> getStageRecords(int start, int COUNT, String modelId) {
        List<Stage> stageList = new ArrayList<>();
        for (int i = start; i < COUNT; i++) {
            stageList.add(new Stage(String.valueOf(i), "stage" + i, "Hi", modelId, i));
        }
        return stageList;
    }
}
/*
StopWatch 'DbQueries': running time = 19593660600 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
4523249600  023%  Parallel Batch Insert : 1000 with Batch : 100
426195600  002%  Paginated Reads
159136800  001%  Cursor Reads
9480108000  048%  Sequential Batch Upsert
5004970600  026%  Parallel Batch Upsert

StopWatch 'DbQueries': running time = 12202782200 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
1894923300  016%  Parallel Batch Insert : 1000 with Batch : 500
1157462800  009%  Paginated Reads
095389800  001%  Cursor Reads
5039392900  041%  Sequential Batch Upsert
4015613400  033%  Parallel Batch Upsert

StopWatch 'DbQueries': running time = 45237504200 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
6684531100  015%  Parallel Batch Insert : 10000 with Batch : 2000
1124687700  002%  Paginated Reads
767933400  002%  Cursor Reads
27887287300  062%  Sequential Batch Upsert
8773064700  019%  Parallel Batch Upsert

StopWatch 'DbQueries': running time = 37127482400 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
6933816800  019%  Parallel Batch Insert : 10000 with Batch : 5000
863097200  002%  Paginated Reads
949240500  003%  Cursor Reads
15538283700  042%  Sequential Batch Upsert
12843044200  035%  Parallel Batch Upsert

StopWatch 'DbQueries': running time = 73304774700 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
9018832600  012%  Parallel Batch Insert : 20000 with Batch : 2000
1987591000  003%  Paginated Reads
1648629100  002%  Cursor Reads
54771976500  075%  Sequential Batch Upsert
5877745500  008%  Parallel Batch Upsert

StopWatch 'DbQueries': running time = 60403425500 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
2058346600  003%  Parallel Batch Insert : 20000 with Batch : 5000
7641829900  013%  Paginated Reads
947748200  002%  Cursor Reads
35027966900  058%  Sequential Batch Upsert
14727533900  024%  Parallel Batch Upsert

StopWatch 'DbQueries': running time = 155915032600 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
10036595700  006%  Parallel Batch Insert : 50000 with Batch : 2000
4589890400  003%  Paginated Reads
2739661100  002%  Cursor Reads
124278111200  080%  Sequential Batch Upsert
14270774200  009%  Parallel Batch Upsert

StopWatch 'DbQueries': running time = 122341474300 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
5178784600  004%  Parallel Batch Insert : 50000 with Batch : 5000
11085402300  009%  Paginated Reads
4177751500  003%  Cursor Reads
82167019500  067%  Sequential Batch Upsert
19732516400  016%  Parallel Batch Upsert
 */