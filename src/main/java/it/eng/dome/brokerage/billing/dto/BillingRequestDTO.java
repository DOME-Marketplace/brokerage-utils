package it.eng.dome.brokerage.billing.dto;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.eng.dome.tmforum.tmf637.v4.model.Product;
import it.eng.dome.tmforum.tmf637.v4.model.ProductPrice;
import it.eng.dome.tmforum.tmf678.v4.model.TimePeriod;

/**
 * This class represents the DTO used by the "billing/bill" API  to calculate a bill \n.
 * This class contains information about the {@link Product} the billingPeriod (i.e., {@link TimePeriod}) and the list of {@link ProductPrice} for which the bill must be calculated.
 */
public class BillingRequestDTO {
	
	private Product product;
	private TimePeriod timePeriod;
	//FIXME - TO REMOVE
	private List<ProductPrice> productPrice;
	
	/** 
	* Class constructor.
	*/
	public BillingRequestDTO(){}
	
	
	/**
	 * Class constructor specifying the {@link Product} the billingPeriod (i.e., {@link TimePeriod}) and the list of {@link ProductPrice} for which the bill must be calculated
	 * 
	 * @param pr A {@link Product} instance for which the bill must be calculate
	 * @param tp A {@link TimePeriod} representing the billingPeriod of the bill 
	 * @param ppl A list of {@link ProductPrice} of the Product that must be included in the bill
	 */
	@JsonCreator
	public BillingRequestDTO(@JsonProperty("product") Product pr, @JsonProperty("timePeriod") TimePeriod tp, @JsonProperty("productPrice") List<ProductPrice> ppl) {
		this.setProduct(pr);
		this.setTimePeriod(tp);
		this.setProductPrice(ppl);
	}

	/**
	 * Returns the {@link Product} to which the bill refers to
	 * 
	 * @return The Product of the bill 
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * Sets the {@link Product} of the bill
	 * 
	 * @param product The Product of the bill
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * Returns the {@link TimePeriod} to which the bill refers to
	 * 
	 * @return The billingPeriod of the bill
	 */ 
	public TimePeriod getTimePeriod() {
		return timePeriod;
	}

	/**
	 * Sets the {@link TimePeriod} of the bill
	 * 
	 * @param timePeriod The billingPeriod of the bill
	 */
	public void setTimePeriod(TimePeriod timePeriod) {
		this.timePeriod = timePeriod;
	}

	/**
	 * Returns the list of the {@link ProductPrice} of the {@link Product} that must be included in the bill
	 * 
	 * @return The list of the ProductPrice of the Product that must be included in the bill
	 */
	public List<ProductPrice> getProductPrice() {
		return productPrice;
	}

	/**
	 * Sets the list of the {@link ProductPrice} of the {@link Product} that must be included in the bill
	 * 
	 * @param productPrice The list of the ProductPrice of the product that must be included in the bill
	 */
	public void setProductPrice(List<ProductPrice> productPrice) {
		this.productPrice = productPrice;
	}

}