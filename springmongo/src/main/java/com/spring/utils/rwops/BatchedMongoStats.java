package com.spring.utils.rwops;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class BatchedMongoStats implements RWOps{

    private MongoTemplate mongoTemplate;

    @Autowired
    public BatchedMongoStats(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void test() {
        System.out.println("StartedTesting");
        getStats(200,1000);
        getStats(500,1000);
        getStats(1000,5000);
        getStats(1000,10000);
        getStats(2000,10000);
        getStats(2000,20000);
        getStats(2000,50000);
        getStats(5000,50000);
        System.out.println("Completed Testing");
    }

    private void getStats(int batchSize, int count) {
        RWOps sequentialBatchRWOps1 = new SequentialBatchRWOps(mongoTemplate, batchSize, count, "Sequential Count : " + count +" | Batch : " + batchSize);
        sequentialBatchRWOps1.test();
        RWOps parallelBatchRWOps1 = new ParallelBatchRWOps(mongoTemplate, batchSize, count, "Parallel Count : " + count +" | Batch : " + batchSize);
        parallelBatchRWOps1.test();
    }

}
/*
Detailed report:
StopWatch 'Sequential Count : 1000 | Batch : 200': running time = 5906253900 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
4647707500  079%  writeOps
1258546400  021%  readOps

Detailed report:
StopWatch 'Parallel Count : 1000 | Batch : 200': running time = 5001786400 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
4649848100  093%  writeOps
351938300  007%  readOps

Detailed report:
StopWatch 'Sequential Count : 1000 | Batch : 500': running time = 4336109000 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
3905720600  090%  writeOps
430388400  010%  readOps

Detailed report:
StopWatch 'Parallel Count : 1000 | Batch : 500': running time = 2428727000 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
2265095300  093%  writeOps
163631700  007%  readOps

Detailed report:
StopWatch 'Sequential Count : 5000 | Batch : 1000': running time = 15834589400 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
14893662800  094%  writeOps
940926600  006%  readOps

Detailed report:
StopWatch 'Parallel Count : 5000 | Batch : 1000': running time = 5440938800 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
5068849200  093%  writeOps
372089600  007%  readOps

Detailed report:
StopWatch 'Sequential Count : 10000 | Batch : 1000': running time = 28738474400 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
27639226500  096%  writeOps
1099247900  004%  readOps

Detailed report:
StopWatch 'Parallel Count : 10000 | Batch : 1000': running time = 10515971000 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
9928388100  094%  writeOps
587582900  006%  readOps

Detailed report:
StopWatch 'Sequential Count : 10000 | Batch : 2000': running time = 20197104700 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
19322026000  096%  writeOps
875078700  004%  readOps

Detailed report:
StopWatch 'Parallel Count : 10000 | Batch : 2000': running time = 7314314900 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
6474048600  089%  writeOps
840266300  011%  readOps

Detailed report:
StopWatch 'Sequential Count : 20000 | Batch : 2000': running time = 32910775400 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
30350338000  092%  writeOps
2560437400  008%  readOps

Detailed report:
StopWatch 'Parallel Count : 20000 | Batch : 2000': running time = 9948745900 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
8325394200  084%  writeOps
1623351700  016%  readOps

Detailed report:
StopWatch 'Sequential Count : 50000 | Batch : 2000': running time = 106503762200 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
100855272500  095%  writeOps
5648489700  005%  readOps

Detailed report:
StopWatch 'Parallel Count : 50000 | Batch : 2000': running time = 16889922800 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
12836444200  076%  writeOps
4053478600  024%  readOps

Detailed report:
StopWatch 'Sequential Count : 50000 | Batch : 5000': running time = 63800887000 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
58166605700  091%  writeOps
5634281300  009%  readOps

Detailed report:
StopWatch 'Parallel Count : 50000 | Batch : 5000': running time = 15128366300 ns
---------------------------------------------
ns         %     Task name
---------------------------------------------
12344320400  082%  writeOps
2784045900  018%  readOps
 */
