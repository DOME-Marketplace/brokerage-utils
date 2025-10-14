package it.eng.dome.brokerage.utils.enumappers;

import com.fasterxml.jackson.databind.module.SimpleModule;

import it.eng.dome.tmforum.tmf633.v4.model.JobStateType;


/**
 * Jackson module for TMF633 API enumerations.
 *
 * <p>This module registers custom serializers and deserializers for all
 * TMForum (TMF633) enum types used within the Service Catalog Management API.
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
 * public Module TMF633EnumModule() {
 *     return new TMF633EnumModule();
 * }
 * </pre>
 */
public class TMF633EnumModule extends SimpleModule {

	private static final long serialVersionUID = -7309359707057468994L;

	public TMF633EnumModule() {
		super(TMF633EnumModule.class.getName());
		
        // JOB STATE
		this.addDeserializer(JobStateType.class, new GenericEnumDeserializer<>(JobStateType.class));
		this.addSerializer(JobStateType.class, new GenericEnumSerializer<>(JobStateType.class));
	}

}
