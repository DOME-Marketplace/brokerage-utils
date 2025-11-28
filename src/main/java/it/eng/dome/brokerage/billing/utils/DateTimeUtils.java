package it.eng.dome.brokerage.billing.utils;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class DateTimeUtils {
	
	/**
	 * Returns the current local date-time with its offset forcibly set to UTC (Z)
	 * while preserving the local clock time.
	 * <p>
	 * This method does <strong>not</strong> convert or shift the time instant.
	 * Instead, it keeps the same local time and simply replaces the local offset
	 * (e.g., +01:00 or +02:00) with UTC (Z).
	 * <p>
	 * Example:
	 * <pre>
	 * Local time: 2025-11-27T18:07+01:00
	 * Result:     2025-11-27T18:07Z
	 * </pre>
	 *
	 * @return an {@link OffsetDateTime} representing the current local time but using UTC as the offset, without altering the local clock time.
	 */
	public static OffsetDateTime getLocalTimeWithUtcOffset1() {
		OffsetDateTime localTime = OffsetDateTime.now(Clock.systemDefaultZone());
    	return localTime.withOffsetSameLocal(ZoneOffset.UTC);        
    }
	
	
	/**
	 * Returns the current date and time in UTC.
	 *
	 * @return the current {@link OffsetDateTime} using {@link ZoneOffset#UTC}
	 */
	public static OffsetDateTime getCurrentUtcTime() {
	    return OffsetDateTime.now(ZoneOffset.UTC);
	}

}
