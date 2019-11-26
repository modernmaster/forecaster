package uk.co.jamesmcguigan.forecaster.repository;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

@Configuration
public class MongodbDataSourceConfig extends AbstractMongoConfiguration {

    @Override
    public final String getDatabaseName() {
        return "forecaster";
    }

    @Override
    public final MongoClient mongoClient() {
        ServerAddress serverAddress = new ServerAddress(
                System.getenv("GUESTBOOK_DB_ADDR"));
        return new MongoClient(serverAddress);
    }
}
