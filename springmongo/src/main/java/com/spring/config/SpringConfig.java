package com.spring.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.connection.ConnectionPoolSettings;
import com.mongodb.connection.SocketSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import java.util.concurrent.TimeUnit;

@Configuration
@ComponentScan("com.spring")
public class SpringConfig {

    /*
        Related docs :
        https://www.mongodb.com/docs/drivers/java/sync/v4.3/fundamentals/connection/mongoclientsettings/#socket-settings
        https://mongodb.github.io/mongo-java-driver/4.3/apidocs/mongodb-driver-core/com/mongodb/MongoClientSettings.Builder.html#applyToConnectionPoolSettings(com.mongodb.Block)
        https://mongodb.github.io/mongo-java-driver/4.3/apidocs/mongodb-driver-core/com/mongodb/connection/ConnectionPoolSettings.html
        https://mongodb.github.io/mongo-java-driver/4.3/apidocs/mongodb-driver-core/com/mongodb/connection/SocketSettings.Builder.html
     */
    @Bean
    public MongoClient mongoClient(){
        ConnectionString connectionString = new ConnectionString("mongodb+srv://dummy222mail:HPnrk88KDhvCqbqh@grp.4231awd.mongodb.net/?retryWrites=true&w=majority&appName=grp");
        ConnectionPoolSettings poolSettings = ConnectionPoolSettings.builder()
                .maxSize(100) // maximum number of connections allowed.
                .minSize(10) // minimum number of connections
                .maxWaitTime(60, TimeUnit.SECONDS) // The maximum time that a thread may wait for a connection to become available.
                .build();

        SocketSettings socketSettings = SocketSettings.builder()
                .connectTimeout(10, TimeUnit.SECONDS) // Sets the maximum time to connect to an available socket before throwing a timeout exception. Default is 10 seconds
                .readTimeout(30, TimeUnit.SECONDS) //  Sets the maximum time to read from an available socket before throwing a timeout exception. Default is 0,i.e.; no timeout
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .applicationName("graph-service")
                .applyToSocketSettings(builder -> builder.applySettings(socketSettings))
                .applyToConnectionPoolSettings(builder -> builder.applySettings(poolSettings))
                .build();
        return MongoClients.create(settings);
    }

    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory(MongoClient client) {
        return new SimpleMongoClientDatabaseFactory(client, "test_mongo");
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory dbFactory) {
        return new MongoTemplate(dbFactory);
    }

}
