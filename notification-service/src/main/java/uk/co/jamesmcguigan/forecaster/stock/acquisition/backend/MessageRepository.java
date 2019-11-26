package uk.co.jamesmcguigan.forecaster.stock.acquisition.backend;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * represents a custom Mongo repository that stores GuestBookEntry objects
 */
public interface MessageRepository extends
    MongoRepository<GuestBookEntry, String> { }
