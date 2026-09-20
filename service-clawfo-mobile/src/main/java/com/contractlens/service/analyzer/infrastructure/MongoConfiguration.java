package com.contractlens.service.analyzer.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Configuration
public class MongoConfiguration {

    @Bean
    public MappingMongoConverter mappingMongoConverter(
            MongoDatabaseFactory databaseFactory,
            MongoCustomConversions conversions,
            MongoMappingContext mappingContext
    ) {

        DbRefResolver resolver =
                new DefaultDbRefResolver(databaseFactory);

        MappingMongoConverter converter =
                new MappingMongoConverter(
                        resolver,
                        mappingContext
                );

        converter.setCustomConversions(conversions);

        // Mengganti '.' menjadi '___DOT___'
        converter.setMapKeyDotReplacement("___DOT___");

        return converter;

    }

}
