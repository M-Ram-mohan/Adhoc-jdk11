package com.spring.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
@ComponentScan("com.spring")
public class SpringConfig {

    // TODO : Decide to choose between Mongo client or Mongo Client factory bean. One gives
    //  more flexibility to listeners and monitors stuff and the other has "Proper exception handling"
    @Bean("client1")
    public MongoClient mongoClient(){
        ConnectionString connectionString = new ConnectionString("mongodb+srv://dummy222mail:HPnrk88KDhvCqbqh@grp.4231awd.mongodb.net/?retryWrites=true&w=majority&appName=grp");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .applicationName("graph-service")
                .build();
        return MongoClients.create(settings);
    }

    @Bean("client2")
    public MongoClientFactoryBean mongoClientFactoryBean(){
        MongoClientFactoryBean mongo = new MongoClientFactoryBean();
        mongo.setConnectionString(new ConnectionString("mongodb+srv://dummy222mail:HPnrk88KDhvCqbqh@grp.4231awd.mongodb.net/?retryWrites=true&w=majority&appName=grp"));
        return mongo;
    }

    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory(@Qualifier("client1") MongoClient client) {
        return new SimpleMongoClientDatabaseFactory(client, "test_mongo");
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory dbFactory) {
        return new MongoTemplate(dbFactory);
    }

}
