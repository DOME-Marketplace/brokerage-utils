package it.eng.dome.brokerage.utils.enumappers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericEnumSerializer<T> extends StdSerializer<T> {

	private static final long serialVersionUID = 2788260409548213373L;
	private final Logger logger = LoggerFactory.getLogger(GenericEnumSerializer.class);

	public GenericEnumSerializer(Class<T> t) {
		super(t);
	}

	@Override
	public void serialize(T value, JsonGenerator gen, SerializerProvider provider) throws IOException {

		try {
			Method getValue = value.getClass().getMethod("getValue");
			Object rawValue = getValue.invoke(value);

			logger.debug("Serializing {} as {}", value.getClass().getSimpleName(), rawValue);

			// check if null
			if (rawValue == null) {
				gen.writeNull();

				// 1️: Date e java.time
			} else if (rawValue instanceof Date || rawValue instanceof TemporalAccessor) {
				provider.defaultSerializeValue(rawValue, gen);

				// 2️: Number
			} else if (rawValue instanceof Number) {
				gen.writeNumber(((Number) rawValue).toString());

				// 3️: Boolean
			} else if (rawValue instanceof Boolean) {
				gen.writeBoolean((Boolean) rawValue);

			} else {
				// 4️: Others
				gen.writeString(String.valueOf(rawValue));
			}

		} catch (NoSuchMethodException e) {
			// fallback in case of missing getValue() => use toString()
			logger.debug("Serializing {} as {}", value.getClass().getSimpleName(), value.toString());
			gen.writeString(value.toString());

		} catch (Exception e) {
			logger.error("Error serializing enum {}", value.getClass().getSimpleName(), e);
			throw new IOException("Error serializing enum " + value.getClass().getSimpleName(), e);
		}
	}
}