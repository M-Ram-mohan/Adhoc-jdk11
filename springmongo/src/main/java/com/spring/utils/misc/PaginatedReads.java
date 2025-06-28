package com.spring.utils.misc;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import pojos.Stage;

import java.util.List;

public class PaginatedReads implements DbRequest{
    public MongoTemplate mongoTemplate;
    private int count, pageSize;
    public PaginatedReads(MongoTemplate template) {
        this.mongoTemplate = template;
    }
    @Override
    public void execute(int COUNT, int BATCH_SIZE) {
        this.count = COUNT;
        this.pageSize = BATCH_SIZE;
        int totalPages = count/pageSize;
        for(int page=0; page<totalPages; page++) {
            Pageable pageable = PageRequest.of(page, pageSize);
            Query query = Query.query(Criteria.where("modelId").is("123")).with(pageable);
            List<Stage> stageList = mongoTemplate.find(query, Stage.class);
            if(stageList.isEmpty()){
                break;
            }
        }
    }
}
