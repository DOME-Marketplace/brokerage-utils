package it.eng.dome.brokerage.utils.enumappers;

import com.fasterxml.jackson.databind.module.SimpleModule;

import it.eng.dome.tmforum.tmf635.v4.model.UsageStatusType;


/**
 * Jackson module for TMF635 API enumerations.
 *
 * <p>This module registers custom serializers and deserializers for all
 * TMForum (TMF635) enum types used within the Usage Management API.
 * It ensures that enums exposing {@code getValue()} and {@code fromValue(String)}
 * methods are properly handled during JSON serialization and deserialization.</p>
 *
 * <p>By registering this module (either manually or via Spring Boot
 * {@link org.springframework.context.annotation.Bean} configuration),
 * the {@link com.fasterxml.jackson.databind.ObjectMapper} can automatically
 * convert TMF enum values to and from their textual representation
 * as defined by the TMForum specification.</p>
 *
 * <p>Example — register in Spring Boot:</p>
 * <pre>
 * {@literal @}Bean
 * public Module TMF635EnumModule() {
 *     return new TMF635EnumModule();
 * }
 * </pre>
 */
public class TMF635EnumModule extends SimpleModule {

	private static final long serialVersionUID = -3617467722075697823L;

	public TMF635EnumModule() {
		super(TMF635EnumModule.class.getName());
		
        // USAGE STATUS
		this.addDeserializer(UsageStatusType.class, new GenericEnumDeserializer<>(UsageStatusType.class));
		this.addSerializer(UsageStatusType.class, new GenericEnumSerializer<>(UsageStatusType.class));
	}

}
