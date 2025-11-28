package it.eng.dome.brokerage.model;

import jakarta.validation.constraints.NotNull;

public enum RecurringPeriod {

    DAY("day"),
    WEEK("week"),
    MONTH("month"),
    YEAR("year");
	
	private final String recurringPeriod;
    
    RecurringPeriod(String recurringPeriod) {
		this.recurringPeriod=recurringPeriod;
	}

	public static RecurringPeriod fromString(@NotNull String recurringPeriod) {

		//String normalized = priceType.toLowerCase().replaceAll("[\\s-]+", "_");
		
		String normalized =recurringPeriod
				.trim()
				.replaceAll("([a-z])([A-Z])", "$1_$2") // camelCase → snake_case
				.toLowerCase()
				.replaceAll("[\\s-]+", "_");
		
		switch (normalized) {
        	case "day":
        	case "days":
        	case "daily":
        		return RecurringPeriod.DAY;
	        case "week":
	        case "weeks":
	        case "weekly":
	            return RecurringPeriod.WEEK;
	        case "month":
	        case "months": 
	        case "monthly":
	            return RecurringPeriod.MONTH;
	        case "year":
	        case "years":
	        case "yearly":
	            return RecurringPeriod.YEAR;
	        default:
	            throw new IllegalArgumentException(String.format("Invalid '%s' recurringChargePeriod. Valid values are: [day, week, month, year]", normalized));
			 }
	}

	@Override
	public String toString() {
		return this.recurringPeriod;
	}
}