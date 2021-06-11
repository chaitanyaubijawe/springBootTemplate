package com.smartdubai.assesment.eShop;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jackson.JsonComponentModule;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class EShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(EShopApplication.class, args);
	}

	@Bean
	public ObjectMapper objectMapper (JsonComponentModule serializerModule) {

		return new ObjectMapper().deactivateDefaultTyping().disable(
			DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).enable(
			SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS).enable(
			MapperFeature.SORT_PROPERTIES_ALPHABETICALLY).enable(
			MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS).registerModule(serializerModule);
	}
}
