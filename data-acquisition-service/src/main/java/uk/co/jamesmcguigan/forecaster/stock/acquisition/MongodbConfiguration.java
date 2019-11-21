package uk.co.jamesmcguigan.forecaster.stock.acquisition;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

@Configuration
public class MongodbConfiguration extends AbstractMongoConfiguration {

    private static final String JOB = "job";
    private static final String JOB_DB_ADDR = "JOB_DB_ADDR";

    @Override
    public final String getDatabaseName() {
        return JOB;
    }

    @Override
    public final MongoClient mongoClient() {
        ServerAddress serverAddress = new ServerAddress(
                System.getenv(JOB_DB_ADDR));
        return new MongoClient(serverAddress);
    }
}
