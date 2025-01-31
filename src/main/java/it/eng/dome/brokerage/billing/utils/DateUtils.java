package it.eng.dome.brokerage.billing.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
	
	private final String PATTERN = "dd-MM-yyyy HH:mm:ss";
	
	/**
	 * Formats the given {@link Instant} into a timestamp string using the pattern "dd-MM-yyyy HH:mm:ss".
	 * The timestamp is converted to the "Europe/Rome" time zone before formatting.
	 *
	 * @param time the {@link Instant} to be formatted
	 * @return a formatted timestamp string in the "dd-MM-yyyy HH:mm:ss" pattern and "Europe/Rome" time zone
	 * @throws DateTimeException if the instant cannot be converted to a zoned date-time
	 */
    public String getFormatterTimestamp(Instant time) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN);
        ZonedDateTime zonedDateTime = time.atZone(ZoneId.of("Europe/Rome"));
    	return zonedDateTime.format(formatter);        
    }
    
    /**
     * Formats a given {@link Instant} into a string representation based on the specified pattern.
     * The timestamp is converted to the "Europe/Rome" time zone before formatting.
     *
     * @param time    The {@link Instant} to format. Must not be null.
     * @param pattern The format pattern as a {@link String}, following {@link DateTimeFormatter} conventions.
     * @return A formatted timestamp string based on the provided pattern.
     * @throws DateTimeException If the pattern is invalid or the time cannot be formatted.
     */
    public String getFormatterTimestamp(Instant time, String pattern) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        ZonedDateTime zonedDateTime = time.atZone(ZoneId.of("Europe/Rome"));
    	return zonedDateTime.format(formatter);        
    }

}
