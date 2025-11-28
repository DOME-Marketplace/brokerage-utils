package it.eng.dome.brokerage.model;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecurringChargePeriod {
	
	private final RecurringPeriod recurringChargePeriodType;
	private final Integer recurringChargePeriodLenght;
	
	public RecurringChargePeriod(RecurringPeriod recurringChargePeriodType, Integer recurringChargePeriodLenght) {
		this.recurringChargePeriodType=recurringChargePeriodType;
		this.recurringChargePeriodLenght=recurringChargePeriodLenght;
	}

	public RecurringPeriod getRecurringChargePeriodType() {
		return recurringChargePeriodType;
	}

	public Integer getRecurringChargePeriodLenght() {
		return recurringChargePeriodLenght;
	}
	
	public static RecurringChargePeriod parse(String input) {

	    if (input == null || input.isBlank()) {
	        throw new IllegalArgumentException("RecurringChargePeriod string cannot be null or empty");
	    }

	    // Normalize input: "1 month", "1Month", "1-month"
	    String normalized = input
	            .trim()
	            .replaceAll("([a-z])([A-Z])", "$1 $2")    // camelCase
	            .toLowerCase()
	            .replaceAll("[\\s-]+", " ");             // spaces and "-"

	    // Regex: numero + unità
	    Matcher m = Pattern.compile("^(\\d+)\\s*([a-zA-Z]+)$").matcher(normalized);
	    if (!m.find()) {
	        throw new IllegalArgumentException(String.format("RecurringChargePeriod Invalid format %s. Expected like: '1 month', '2 weeks', etc.",normalized));
	    }

	    Integer length = Integer.parseInt(m.group(1));
	    String periodStr = m.group(2);  // day / days / daily / week / weeks...
	    RecurringPeriod period = RecurringPeriod.fromString(periodStr);

	    return new RecurringChargePeriod(period, length);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RecurringChargePeriod other = (RecurringChargePeriod) obj;
		return Objects.equals(recurringChargePeriodLenght, other.recurringChargePeriodLenght)
				&& recurringChargePeriodType == other.recurringChargePeriodType;
	}
    
	@Override
	public int hashCode() {
		return Objects.hash(recurringChargePeriodLenght, recurringChargePeriodType);
	}
	
	@Override
	public String toString() {
		//return "RecurringChargePeriod [recurringChargePeriodType=" + recurringChargePeriodType
		//		+ ", recurringChargePeriodLenght=" + recurringChargePeriodLenght + "]";
		
		return recurringChargePeriodLenght +" "+ recurringChargePeriodType.toString();
	}

}
