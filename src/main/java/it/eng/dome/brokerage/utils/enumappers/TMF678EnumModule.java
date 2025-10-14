package it.eng.dome.brokerage.utils.enumappers;

import com.fasterxml.jackson.databind.module.SimpleModule;

import it.eng.dome.tmforum.tmf678.v4.model.StateValue;
import it.eng.dome.tmforum.tmf678.v4.model.StateValues;


/**
 * Jackson module for TMF678 API enumerations.
 *
 * <p>This module registers custom serializers and deserializers for all
 * TMForum (TMF678) enum types used within the Applied Customer Billing Rate and Customer Bill APIs.
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
 * public Module TMF678EnumModule() {
 *     return new TMF678EnumModule();
 * }
 * </pre>
 */
public class TMF678EnumModule extends SimpleModule {

	private static final long serialVersionUID = 7475681692655850207L;

	public TMF678EnumModule() {
		super(TMF678EnumModule.class.getName());
		
        // STATE VALUE
		this.addDeserializer(StateValue.class, new GenericEnumDeserializer<>(StateValue.class));
		this.addSerializer(StateValue.class, new GenericEnumSerializer<>(StateValue.class));
		
        // STATE VALUES
		this.addDeserializer(StateValues.class, new GenericEnumDeserializer<>(StateValues.class));
		this.addSerializer(StateValues.class, new GenericEnumSerializer<>(StateValues.class));
	}

}
