package it.eng.dome.brokerage.utils.enumappers;

import com.fasterxml.jackson.databind.module.SimpleModule;

import it.eng.dome.tmforum.tmf632.v4.model.IndividualStateType;
import it.eng.dome.tmforum.tmf632.v4.model.OrganizationStateType;


/**
 * Jackson module for TMF632 API enumerations.
 *
 * <p>This module registers custom serializers and deserializers for all
 * TMForum (TMF632) enum types used within the API-Party API.
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
 * public Module TMF632EnumModule() {
 *     return new TMF632EnumModule();
 * }
 * </pre>
 */
public class TMF632EnumModule extends SimpleModule {

	private static final long serialVersionUID = 1895661328749544349L;

	public TMF632EnumModule() {
        super(TMF632EnumModule.class.getName());

        // INDIVIDUAL STATE
        this.addSerializer(IndividualStateType.class, new GenericEnumSerializer<>(IndividualStateType.class));
        this.addDeserializer(IndividualStateType.class, new GenericEnumDeserializer<>(IndividualStateType.class));

        // ORGANIZATION STATE
        this.addSerializer(OrganizationStateType.class, new GenericEnumSerializer<>(OrganizationStateType.class));
        this.addDeserializer(OrganizationStateType.class, new GenericEnumDeserializer<>(OrganizationStateType.class));

	}

}
