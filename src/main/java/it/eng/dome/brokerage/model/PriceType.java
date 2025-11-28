package it.eng.dome.brokerage.model;

import it.eng.dome.tmforum.tmf620.v4.model.ProductOfferingPrice;
import jakarta.validation.constraints.NotNull;

public enum PriceType {

	/**
	 * One off priceType
	 */
    ONE_TIME("one-time"),
    /**
     * Recurring prepaid  priceType
     */
    RECURRING_PREPAID("recurring-prepaid"),
    /**
     * Recurring postpaid priceType
     */
    RECURRING_POSTPAID("recurring-postpaid"),
    
    /**
     * Recurring priceType. This priceType is RECURRING_POSTPAID by default.
     */
    RECURRING("recurring"),
    
    /**
     * Pay-per-Use use case. This use case is RECURRING_POSTPAID by default.
     */
    USAGE("usage"),
    
    /**
     * Discount priceType, used in {@link ProductOfferingPrice} representing a price alteration
     */
    DISCOUNT("discount"),
    
    /**
     * Custom priceType defined by the Provider
     */
    CUSTOM("custom");
	
	private final String priceType;
	
	PriceType(String priceType) {
		this.priceType=priceType;
	}

	public static PriceType fromString(@NotNull String priceType) {

		//String normalized = priceType.toLowerCase().replaceAll("[\\s-]+", "_");
		
		String normalized =priceType
				.trim()
				.replaceAll("([a-z])([A-Z])", "$1_$2") // camelCase → snake_case
				.toLowerCase()
				.replaceAll("[\\s-]+", "_");
		
		switch (normalized) {
         case "onetime":
         case "one_time":
             return PriceType.ONE_TIME;
         case "recurring_prepaid":
         case "recurringprepaid":
             return PriceType.RECURRING_PREPAID;
         case "recurring":
        	 return PriceType.RECURRING;
         case "recurring_postpaid": 
         case "recurringpostpaid":
        	 return PriceType.RECURRING_POSTPAID;
         case "usage":
         case "pay_per_use":
         case "payperuse":
             return PriceType.USAGE;
         case "discount":
        	 return PriceType.DISCOUNT;
         case "custom":
             return PriceType.CUSTOM;
         default:
             throw new IllegalArgumentException(String.format("Invalid '%s' priceType: . Valid values are: "
             		+ "[onetime, recurring, recurring_prepaid, recurring_postpaid, usage or payperuse, discount, custom]",normalized));
		 } 
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return priceType;
	}

	
    
}

