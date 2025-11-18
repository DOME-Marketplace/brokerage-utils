package it.eng.dome.brokerage.utils.enumappers;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import it.eng.dome.brokerage.exception.IllegalEnumException;

public class GenericEnumDeserializer<T> extends StdDeserializer<T> {

	private static final long serialVersionUID = 2027123572912834740L;
	private final Logger logger = LoggerFactory.getLogger(GenericEnumDeserializer.class);
	private final Class<T> enumClass;

    public GenericEnumDeserializer(Class<T> vc) {
        super(vc);
        this.enumClass = vc;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String text = p.getText();
        
        try {
            Method fromValue = enumClass.getMethod("fromValue", String.class);
            T value = (T) fromValue.invoke(null, text);
            logger.debug("Deserialized {} from '{}'", enumClass.getSimpleName(), text);
            return value;
        
        } catch (NoSuchMethodException e) {
        	// fallback in case of missing fromValue() => use Enum.valueOf()
        	T value = (T) Enum.valueOf((Class<Enum>) enumClass.asSubclass(Enum.class), text);
            logger.debug("Deserialized {} from '{}' using Enum.valueOf()", enumClass.getSimpleName(), text);
            return value;
        } catch (Exception e) {
        	logger.warn("Valid {} enum: {}", enumClass.getSimpleName(), getEnumValues());
        	logger.error("Error deserializing enum {} from '{}'", enumClass.getSimpleName(), text);
        	throw new IllegalEnumException("Unexpected value '" + text + "' for enum " + enumClass.getSimpleName(), e);
        }
    }
    

	private String[] getEnumValues() {
		if (!enumClass.isEnum()) {
			return new String[0];
		}
		
		Object[] constants = enumClass.getEnumConstants();
		
		return Arrays.stream(constants).map(constant -> {
			try {
				Method getValue = constant.getClass().getMethod("getValue");
				return String.valueOf(getValue.invoke(constant));
			} catch (Exception e) {
				return constant.toString();
			}
		}).toArray(String[]::new);
	}
}