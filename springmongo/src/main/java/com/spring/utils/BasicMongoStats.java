package com.spring.utils;


import com.spring.utils.rwops.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/*
Note :
1. Update could be helpful in upserting in batch
2. Retrieved values are converted into the desired target type in this case, String. It is also possible to map the values to a more complex type if the stored field contains a document.
 */
@Service
public class BasicMongoStats {

    private  MongoTemplate mongoTemplate;

    @Autowired
    public BasicMongoStats(MongoTemplate mongoTemplate) {
        System.out.println("StartedTesting");
        RWOps sequentialRWOps = new SequentialRWOps(mongoTemplate);
        sequentialRWOps.test();
        RWOps parallelOps = new ParallelRWOps(mongoTemplate);
        parallelOps.test();
        RWOps sequentialBatchRWOps = new SequentialBatchRWOps(mongoTemplate);
        sequentialBatchRWOps.test();
        RWOps parallelBatchRWOps = new ParallelBatchRWOps(mongoTemplate);
        parallelBatchRWOps.test();
        System.out.println("Completed Testing");
    }

/*
StopWatch 'SequentialRWOps': running time = 67095189700 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
35551093000  053%  writeOps
31544096700  047%  readOps

StopWatch 'ParallelRWOps': running time = 28751686300 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
18568484800  065%  writeOps
10183201500  035%  readOps

StopWatch 'SequentialBatchRWOps': running time = 10860045700 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
10439584100  096%  writeOps
420461600  004%  readOps

StopWatch 'ParallelBatchRWOps': running time = 3435687800 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
3313264000  096%  writeOps
122423800  004%  readOps
*/



}
