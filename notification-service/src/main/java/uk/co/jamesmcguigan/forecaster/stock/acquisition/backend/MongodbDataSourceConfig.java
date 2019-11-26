package uk.co.jamesmcguigan.forecaster.stock.acquisition.backend;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

/**
 * manages Mongo configuration data
 */
@Configuration
public class MongodbDataSourceConfig extends AbstractMongoConfiguration {

    private static final String FORECASTER = "forecaster";

    @Override
    public final String getDatabaseName() {
        return FORECASTER;
    }

    @Override
    public final MongoClient mongoClient() {
        ServerAddress serverAddress = new ServerAddress(
            System.getenv("GUESTBOOK_DB_ADDR"));
        return new MongoClient(serverAddress);
    }
}
