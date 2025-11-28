package it.eng.dome.brokerage.billing.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import it.eng.dome.brokerage.api.ProductCatalogManagementApis;
import it.eng.dome.brokerage.model.PriceType;
import it.eng.dome.brokerage.model.RecurringChargePeriod;
import it.eng.dome.brokerage.model.RecurringPeriod;
import it.eng.dome.tmforum.tmf620.v4.ApiException;
import it.eng.dome.tmforum.tmf620.v4.model.BundledProductOfferingPriceRelationship;
import it.eng.dome.tmforum.tmf620.v4.model.ProductOfferingPrice;
import it.eng.dome.tmforum.tmf620.v4.model.ProductOfferingPriceRelationship;
import it.eng.dome.tmforum.tmf620.v4.model.ProductSpecificationCharacteristicValueUse;
import it.eng.dome.tmforum.tmf620.v4.model.TimePeriod;
import jakarta.validation.constraints.NotNull;

public class ProductOfferingPriceUtils {
	
	private static Logger logger=LoggerFactory.getLogger(ProductOfferingPriceUtils.class);
	
	private final static Date BEGIN = (new GregorianCalendar(1900, Calendar.JANUARY, 1)).getTime();
	private final static Date END = (new GregorianCalendar(2100, Calendar.DECEMBER, 31)).getTime();
	
	
	/**
	 * Retrieves the {@link ProductOfferingPrice} with the specified {@link ProductOfferingPrice}'s identifier
	 * @param popRef The identifier of the ProductOfferigPrice to retrieve
	 * @param popApis A {@link ProductCatalogManagementApis} instance
	 * @return The ProductOfferingPrice referenced by the specified {@link ProductOfferingPrice}'s identifier 
	 * @throws IllegalArgumentException If the referenced ProductOfferingPrice does not exist  
	 * @throws ApiException If the API call to retrieve the ProductOfferingPrice fails or the resource cannot be retrieved
	 */
	public static ProductOfferingPrice getProductOfferingPrice(@NotNull String popRefId, @NotNull ProductCatalogManagementApis popApis) throws IllegalArgumentException, ApiException {
		
		//ProductOfferingPriceRef popRef = pp.getProductOfferingPrice();
		/*if(popRef==null) 
			throw new IllegalArgumentException("The ProductOfferingPriceRef is null");*/
		
		ProductOfferingPrice pop=popApis.getProductOfferingPrice(popRefId, null);
		
		if(pop==null)			
			throw new IllegalArgumentException(String.format("The ProductOfferingPrice '%s' is null", popRefId));
		
		return pop;
	}
	
	/**
	 * Retrieves the list of {@link ProductOfferingPrice} from a list of {@link BundledProductOfferingPriceRelationship}
	 *   
	 * @param popRelationships A list of BundledProductOfferingPriceRelationship referring the ProductOfferingPrice(s)
	 * @param popApis  A {@link ProductCatalogManagementApis} instance
	 * @return The list of {@link ProductOfferingPrice} referenced in the list of BundledProductOfferingPriceRelationship 
	 * @throws IllegalArgumentException If a ProductOfferingPrice referred to does not exist
	 * @throws ApiException If the API call to retrieve a ProductOfferingPrice fails or the resource cannot be retrieved
	 */
	public static List<ProductOfferingPrice> getBundledProductOfferingPrices(@NotNull List<BundledProductOfferingPriceRelationship> popRelationships, @NotNull ProductCatalogManagementApis popApis) throws IllegalArgumentException, ApiException{
		logger.debug("Retrieving of ProductOfferingPrice bundled relationships...");
		List<ProductOfferingPrice> popList=new ArrayList<ProductOfferingPrice>();
		
		for(BundledProductOfferingPriceRelationship popRel: popRelationships) {
			
			ProductOfferingPrice pop = popApis.getProductOfferingPrice(popRel.getId(),null);
			
			if(pop==null) {
				throw new IllegalArgumentException(String.format("The ProductOfferingPrice '%s' is null", popRel.getId()));
			}
			
			popList.add(pop);
		}
		
		return popList;
	}
	
	/**
	 * Retrieves the list of {@link ProductOfferingPrice} from a list of {@link ProductOfferingPriceRelationship}
	 *   
	 * @param popRelationships A list of ProductOfferingPriceRelationship referring the ProductOfferingPrice(s)
	 * @param popApis  A {@link ProductCatalogManagementApis} instance
	 * @return The list of {@link ProductOfferingPrice} referenced in the list of ProductOfferingPriceRelationship 
	 * @throws IllegalArgumentException If a ProductOfferingPrice referred to does not exist
	 * @throws ApiException If the API call to retrieve a ProductOfferingPrice fails or the resource cannot be retrieved
	 */
	public static List<ProductOfferingPrice> getProductOfferingPriceRelationships(@NotNull List<ProductOfferingPriceRelationship> popRelationships, @NotNull ProductCatalogManagementApis popApis) throws IllegalArgumentException, ApiException{
		logger.debug("Retrieving of ProductOfferingPrice relationships...");
		List<ProductOfferingPrice> popList=new ArrayList<ProductOfferingPrice>();
		
		for(ProductOfferingPriceRelationship popRel: popRelationships) {
			
			ProductOfferingPrice pop = popApis.getProductOfferingPrice(popRel.getId(),null);
			
			if(pop==null) {
				throw new IllegalArgumentException(String.format("The ProductOfferingPrice '%s' is null", popRel.getId()));
			}
			
			popList.add(pop);
		}
		
		return popList;
	}
	
	/**
	 * Retrieves the {@link RecurringChargePeriod} of a {@link ProductOfferingPrice}
	 *  
	 * @param pop The ProductOfferingPrice instance from which the RecurringChargePeriod must be retrieved
	 * @return The RecurringChargePeriod of the specified ProductOfferingPrice
	 * @throws IllegalArgumentException If the ProductOfferingPrice contains unexpected values
	 */
	public static RecurringChargePeriod getRecurringChargePeriod(@NotNull ProductOfferingPrice pop) throws IllegalArgumentException{
		
		Integer periodLenght=pop.getRecurringChargePeriodLength();
		RecurringPeriod periodType=getRecurringPeriodType(pop);
		
		RecurringChargePeriod rcp=new RecurringChargePeriod(periodType,periodLenght);
		
		if(periodLenght!=null && periodType!=null) { 
			if(periodLenght<=0)
				throw new IllegalArgumentException(String.format("The 'recurringChargePeriodLenght' must be greater than zero in the ProductOfferingPrice '%s'", pop.getId()));
			
			//rcp.setRecurringChargePeriodLenght(periodLenght);
			//rcp.setRecurringChargePeriodType(periodType);
			
			return rcp;
		}
		return null;
	}
	
	/**
	 * Retrieves the {@link RecurringPeriod} of a {@link ProductOfferingPrice}
	 * @param pop The ProductOfferingPrice from which the RecurringPeriod must be retrieved
	 * @return The RecurringPeriod of the specified ProductOfferingPrice
	 * @throws IllegalArgumentException If the ProductOfferingPrice contains unexpected values
	 */
	/*public static RecurringPeriod getRecurringPeriodType(@NotNull ProductOfferingPrice pop) throws IllegalArgumentException{
		 
		String popRecChargePeriodType=pop.getRecurringChargePeriodType();
		
		if(popRecChargePeriodType!=null) {
			String normalized = popRecChargePeriodType.toLowerCase().replaceAll("[\\s-]+", "_");
		
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
	             throw new IllegalArgumentException(String.format("Invalid '%s' recurringChargePeriod in the ProductOfferingPrice '%s'", normalized,pop.getId()));
			 }
		}else {
			throw new IllegalArgumentException(String.format("Error in ProductOfferingPrice '%s': the RecurringChargePeriodType is null", pop.getId()));
		}
	}*/
	
	public static RecurringPeriod getRecurringPeriodType(@NotNull ProductOfferingPrice pop) throws IllegalArgumentException{
		 
		String popRecChargePeriodType=pop.getRecurringChargePeriodType();
		
		if(popRecChargePeriodType!=null) {
			return RecurringPeriod.fromString(popRecChargePeriodType); 
		}else {
			throw new IllegalArgumentException(String.format("Error in ProductOfferingPrice '%s': the RecurringChargePeriodType is null", pop.getId()));
		}
	}
	
	/**
	 * Retrieves the {@link PriceType} of a {@link ProductOfferingPrice}
	 * @param pop he ProductOfferingPrice from which the PriceType must be retrieved
	 * @return The PriceType of the specified ProductOfferingPrice
	 * @throws IllegalArgumentException If the ProductOfferingPrice contains unexpected values
	 */
	/*public static PriceType getPriceType(@NotNull ProductOfferingPrice pop) throws IllegalArgumentException{
		 
		String popPriceType=pop.getPriceType();

		if(popPriceType!=null) {
			String normalized = popPriceType.toLowerCase().replaceAll("[\\s-]+", "_");
			
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
	             throw new IllegalArgumentException(String.format("Invalid '%s' priceType in the ProductOfferingPrice '%s'", normalized, pop.getId()));
			 } 
		}else {
			throw new IllegalArgumentException(String.format("Error in ProductOfferingPrice '%s': the priceType is null", pop.getId()));
		}
	}*/
	
	public static PriceType getPriceType(@NotNull ProductOfferingPrice pop) throws IllegalArgumentException{
		
		String popPriceType=pop.getPriceType();
		
		if(popPriceType!=null && !popPriceType.isEmpty())
			return PriceType.fromString(popPriceType);
		else {
			throw new IllegalArgumentException(String.format("Error in ProductOfferingPrice '%s': the priceType is null", pop.getId()));
		}
	}
	
	/**
	 * Checks if the specified {@link ProductOfferingPrice} is a ONE_TIME {@link PriceType}
	 * @param pop A ProductOfferingPrice instance
	 * @return True if the specified ProductOfferingPrice is a ONE_TIME PriceType, false otherwise
	 */
	public static boolean isPriceTypeOneTime(@NotNull ProductOfferingPrice pop){
		if(getPriceType(pop)!=null)
			return getPriceType(pop).equals(PriceType.ONE_TIME);
		else
			return false;
	}
	
	/**
	 * Checks if the specified {@link ProductOfferingPrice} is a CUSTOM {@link PriceType}
	 * @param pop A ProductOfferingPrice instance
	 * @return True if the specified ProductOfferingPrice is a CUSTOM PriceType, false otherwise
	 */
	public static boolean isPriceTypeCustom(@NotNull ProductOfferingPrice pop) {
		if(getPriceType(pop)!=null)
			return getPriceType(pop).equals(PriceType.CUSTOM);
		else
			return false;
	}
	
	/**
	 * Checks if the specified {@link ProductOfferingPrice} is RECURRING {@link PriceType}
	 * @param pop A ProductOfferingPrice instance
	 * @return True if the specified ProductOfferingPrice is a RECURRING PriceType, false otherwise
	 */
	public static boolean isPriceTypeRecurring(@NotNull ProductOfferingPrice pop) throws IllegalArgumentException{
		/*if(getPriceType(pop)!=null)
			return getPriceType(pop).equals(PriceType.RECURRING_POSTPAID) || getPriceType(pop).equals(PriceType.RECURRING_PREPAID);
		else
			return false;
			*/
		if(getPriceType(pop)!=null)
			return getPriceType(pop).equals(PriceType.RECURRING);
		else
			return false;
	}
	
	/**
	 * Checks if the specified {@link ProductOfferingPrice} is a RECURRING_POSTPAID {@link PriceType}
	 * @param pop A ProductOfferingPrice instance
	 * @return True if the specified ProductOfferingPrice is a RECURRING_POSTPAID PriceType, false otherwise
	 */
	public static boolean isPriceTypeRecurringPostpaid(@NotNull ProductOfferingPrice pop) throws IllegalArgumentException{
		if(getPriceType(pop)!=null)
			return getPriceType(pop).equals(PriceType.RECURRING_POSTPAID);
		else
			return false;
	}
	
	/**
	 * Checks if the specified {@link ProductOfferingPrice} is a RECURRING_PREPAID {@link PriceType}
	 * @param pop A ProductOfferingPrice instance
	 * @return True if the specified ProductOfferingPrice is a RECURRING_PREPAID PriceType, false otherwise
	 */
	public static boolean isPriceTypeRecurringPrepaid(@NotNull ProductOfferingPrice pop) throws IllegalArgumentException{
		if(getPriceType(pop)!=null)
			return getPriceType(pop).equals(PriceType.RECURRING_PREPAID);
		else
			return false;
	}
	
	/**
	 * Checks if the specified {@link ProductOfferingPrice} is a Usage (i.e., pay-per-use) priceType
	 * 
	 * @param pop A ProductOfferingPrice instance
	 * @return True if the specified ProductOfferingPrice is a Usage priceType, false otherwise
	 */
	public static boolean isPriceTypeUsage(@NotNull ProductOfferingPrice pop) {
		/*String popPriceType=pop.getPriceType();

		if(popPriceType!=null) {
			String normalized = popPriceType.toLowerCase().replaceAll("[\\s-]+", "_");
			
			switch (normalized) {
	         case "usage":
	         case "pay_per_use":
	         case "payperuse":
	             return true;
	         
			}
		}
		
		return false;*/
		if(getPriceType(pop)!=null)
			return getPriceType(pop).equals(PriceType.USAGE);
		else
			return false;
	}
	
	/**
	 * Checks if the specified {@link ProductOfferingPrice} is a DISCOUNT {@link PriceType}
	 * @param pop A ProductOfferingPrice instance
	 * @return True if the specified ProductOfferingPrice is a DISCOUNT PriceType, false otherwise
	 */
	public static boolean isPriceTypeDiscount(@NotNull ProductOfferingPrice pop) throws IllegalArgumentException{
		if(getPriceType(pop)!=null)
			return getPriceType(pop).equals(PriceType.DISCOUNT);
		else
			return false;
	}
	
	/**
	 * Checks if the specified {@link ProductOfferingPrice} belongs to the RECURRING category, i.e., the {@link PriceType} is RECURRING, RECURRING_POSTPAID, RECURRING_PREPAID or USAGE.
	 * @param pop A ProductOfferingPrice instance
	 * @return True if the specified ProductOfferingPrice is a RECURRING, RECURRING_POSTPAID, RECURRING_PREPAID or USAGE {@link PriceType}, false otherwise
	 */
	public static boolean isPriceTypeInRecurringCategory(@NotNull ProductOfferingPrice pop) throws IllegalArgumentException{
		if(getPriceType(pop)!=null)
			return getPriceType(pop).equals(PriceType.RECURRING) || getPriceType(pop).equals(PriceType.RECURRING_POSTPAID) || getPriceType(pop).equals(PriceType.RECURRING_PREPAID) || getPriceType(pop).equals(PriceType.USAGE);
		else
			return false;
	}
	
	/**
	 * Checks if the specified {@link ProductOfferingPrice} belongs to the RECURRING_POSTPAID category, i.e., the {@link PriceType} is RECURRING, RECURRING_POSTPAID or USAGE.
	 * @param pop A ProductOfferingPrice instance
	 * @return True if the specified ProductOfferingPrice is a RECURRING, RECURRING_POSTPAID or USAGE {@link PriceType}, false otherwise
	 */
	public static boolean isPriceTypeInRecurringPostpaidCategory(@NotNull ProductOfferingPrice pop) throws IllegalArgumentException{
		if(getPriceType(pop)!=null)
			return getPriceType(pop).equals(PriceType.RECURRING) || getPriceType(pop).equals(PriceType.RECURRING_POSTPAID) || getPriceType(pop).equals(PriceType.USAGE);
		else
			return false;
	}
	
	/**
	 * Checks if the {@link ProductOfferingPrice} contains pop relationships
	 * @param pop the {@link ProductOfferingPrice} to check
	 * @return true if pop relationships are present, false otherwise
	 */
	public static boolean hasRelationships(@NotNull ProductOfferingPrice pop) {
		return !CollectionUtils.isEmpty(pop.getPopRelationship());
	}
	
	/**
	 * Checks if the {@link ProductOfferingPrice} contains {@link ProductSpecificationCharacteristicValueUse}
	 * @param pop the {@link ProductOfferingPrice} to check
	 * @return true if productSpecificationCharacteristicValueUse(s) are present, false otherwise
	 */
	public static boolean hasProdSpecCharValueUses(@NotNull ProductOfferingPrice pop) {
		return (!CollectionUtils.isEmpty(pop.getProdSpecCharValueUse()));
	}
	
	public static boolean  isActive (@NotNull ProductOfferingPrice pop){
		return ("active".equalsIgnoreCase(pop.getLifecycleStatus()) || "launched".equalsIgnoreCase(pop.getLifecycleStatus()));
	}
	
	public static boolean  isValid (@NotNull ProductOfferingPrice pop){
		if (pop.getValidFor() == null)
			return true;
		
		final Date when = new Date();
		final Date start;
		final TimePeriod period=pop.getValidFor();
		
		if (pop.getValidFor().getStartDateTime() != null) {
			var tmp = period.getStartDateTime();
			start = (new GregorianCalendar(tmp.getYear(), tmp.getMonthValue() - 1, tmp.getDayOfMonth())).getTime();
		} else 
			start = BEGIN;
		
		final Date end;
		if (period.getEndDateTime() != null) {
			var tmp = period.getEndDateTime();
			end = (new GregorianCalendar(tmp.getYear(), tmp.getMonthValue() - 1, tmp.getDayOfMonth())).getTime();
		} else 
			end = END;
		
		return (when.compareTo(start) >= 0 && when.compareTo(end) <= 0);
	}
	
	public static boolean isForfaitPrice(@NotNull ProductOfferingPrice pop) {
		return ((pop.getUnitOfMeasure()==null) || (pop.getUnitOfMeasure()!=null && "unit".equalsIgnoreCase(pop.getUnitOfMeasure().getUnits()) && pop.getUnitOfMeasure().getAmount()==1));
	}
	
	public static boolean isBundled(@NotNull ProductOfferingPrice pop) {
		return pop.getIsBundle();
	}
	
}
