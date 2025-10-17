package it.eng.dome.brokerage.utils.enumappers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;


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

				// 1️: java.util.Date 
			} else if (rawValue instanceof Date) {
				provider.defaultSerializeValue(rawValue, gen);

				// 2: DateTime and java.time
			}else if (rawValue instanceof TemporalAccessor) {
			    if (rawValue instanceof OffsetDateTime) {
			        gen.writeString(((OffsetDateTime) rawValue).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
			    } else if (rawValue instanceof LocalDateTime) {
			        gen.writeString(((LocalDateTime) rawValue).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
			    } else if (rawValue instanceof LocalDate) {
			        gen.writeString(((LocalDate) rawValue).format(DateTimeFormatter.ISO_LOCAL_DATE));
			    } else if (rawValue instanceof LocalTime) {
			        gen.writeString(((LocalTime) rawValue).format(DateTimeFormatter.ISO_LOCAL_TIME));
			    } else if (rawValue instanceof ZonedDateTime) {
			        gen.writeString(((ZonedDateTime) rawValue).format(DateTimeFormatter.ISO_ZONED_DATE_TIME));
			    } else {
			        // generic fallback TemporalAccessor
			        provider.defaultSerializeValue(rawValue, gen);
			    }
				
				// 2️: Number
			} else if (rawValue instanceof Number) {
				if (rawValue instanceof BigDecimal) {
			        gen.writeNumber((BigDecimal) rawValue);
			    } else if (rawValue instanceof BigInteger) {
			        gen.writeNumber((BigInteger) rawValue);
			    } else if (rawValue instanceof Integer) {
			        gen.writeNumber((Integer) rawValue);
			    } else if (rawValue instanceof Long) {
			        gen.writeNumber((Long) rawValue);
			    } else if (rawValue instanceof Double) {
			        gen.writeNumber((Double) rawValue);
			    } else if (rawValue instanceof Float) {
			        gen.writeNumber((Float) rawValue);
			    } else if (rawValue instanceof Short) {
			        gen.writeNumber((Short) rawValue);
			    } else if (rawValue instanceof Byte) {
			        gen.writeNumber((Byte) rawValue);
			    } else {
			        // generic fallback
			        gen.writeNumber(rawValue.toString());
			    }

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