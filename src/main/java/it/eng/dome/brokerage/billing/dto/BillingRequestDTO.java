package it.eng.dome.brokerage.billing.dto;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.eng.dome.tmforum.tmf637.v4.model.Product;
import it.eng.dome.tmforum.tmf637.v4.model.ProductPrice;
import it.eng.dome.tmforum.tmf678.v4.model.TimePeriod;

/**
 * This class represents the DTO used by the "billing/bill" API  to calculate a bill \n.
 * This class contains information about the identifier of the {@link Product}, the billingPeriod (i.e., {@link TimePeriod}) and the list of {@link ProductPrice} for which the bill must be calculated.
 */
public class BillingRequestDTO {
	
	private String productId;
	private TimePeriod billingPeriod;
	//FIXME - TO REMOVE
	private List<ProductPrice> productPrice;
	
	/** 
	* Class constructor.
	*/
	public BillingRequestDTO(){}
	
	
	/**
	 * Class constructor specifying the {@link Product} the billingPeriod (i.e., {@link TimePeriod}) and the list of {@link ProductPrice} for which the bill must be calculated
	 * 
	 * @param productId An identifier of the {@link Product} for which the bill must be calculate
	 * @param billingPeriod A {@link TimePeriod} representing the billingPeriod of the bill 
	 * @param ppl A list of {@link ProductPrice} of the Product that must be included in the bill
	 */
	@JsonCreator
	public BillingRequestDTO(@JsonProperty("productId") String productId, @JsonProperty("billingPeriod") TimePeriod billingPeriod, @JsonProperty("productPrice") List<ProductPrice> ppl) {
		this.setProductId(productId);
		this.setBillingPeriod(billingPeriod);
		this.setProductPrice(ppl);
	}

	/**
	 * Returns the identifier of the {@link Product} to which the bill refers to
	 * 
	 * @return The identifier of the Product for which the bill must be calculated 
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * Sets the identifier of the {@link Product} for which the bill must be calculated 
	 * 
	 * @param productId The identifier of the Product to which the bill refers to
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * Returns the {@link TimePeriod} to which the bill refers to
	 * 
	 * @return The billingPeriod of the bill
	 */ 
	public TimePeriod getBillingPeriod() {
		return billingPeriod;
	}

	/**
	 * Sets the {@link TimePeriod} of the bill
	 * 
	 * @param billingPeriod The billingPeriod of the bill
	 */
	public void setBillingPeriod(TimePeriod billingPeriod) {
		this.billingPeriod = billingPeriod;
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