package com.playtomic.tests.wallet.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
@EnableMongoRepositories(basePackages = "com.playtomic.tests.wallet.repository")
public class MongoConfig extends AbstractMongoClientConfiguration {

	@Value("${spring.data.mongodb.uri}")
	private String mongoURI;

	@Value("${mongo.db.name}")
	private String mongoDbName;

	@Bean
	MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
		return new MongoTransactionManager(dbFactory);
	}
	

	@Override
	protected String getDatabaseName() {
		return mongoDbName;
	}

	@Override
	public MongoClient mongoClient() {
		final ConnectionString connectionString = new ConnectionString(mongoURI);
		final MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
				.applyConnectionString(connectionString)
				.readConcern(ReadConcern.LOCAL)
                .writeConcern(WriteConcern.W1)
                .readPreference(ReadPreference.primary())
				.build();
		return MongoClients.create(mongoClientSettings);
	}
}
