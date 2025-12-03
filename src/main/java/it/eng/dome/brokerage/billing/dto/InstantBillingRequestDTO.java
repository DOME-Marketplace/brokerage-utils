package it.eng.dome.brokerage.billing.dto;


import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.eng.dome.tmforum.tmf637.v4.model.Product;

/**
 * This class represents the DTO used by the "billing/instantBill" API  to calculate a bill in a specific date\n.
 * 
 * This class contains information about the {@link Product} with price components and an {@link OffsetDateTime} representing the moment on which the bill is required.
 */
public class InstantBillingRequestDTO {
	
	private Product product;
	private OffsetDateTime date;

	/** 
	* Class constructor.
	*/
	public InstantBillingRequestDTO(){}
	
	
	/**
	 * Class constructor specifying the {@link Product} and the {@link OffsetDateTime} for which the bill must be calculated
	 * 
	 * @param product The {@link Product} for which the bill must be calculate
	 * @param date A {@link OffsetDateTime} representing the date on which the bill is required
	 */
	@JsonCreator
	public InstantBillingRequestDTO(@JsonProperty("product") Product product, @JsonProperty("date") OffsetDateTime date) {
		this.setProduct(product);
		this.setDate(date);
	}

	/**
	 * Returns the {@link Product}
	 * 
	 * @return The Product for which the bill must be calculated 
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * Sets the {@link Product} for which the bill must be calculated 
	 * 
	 * @param product The Product to set
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

    /**
     * Returns the {@link OffsetDateTime} representing the date for which the bill is requested
     * 
     * @return the date
     */
	public OffsetDateTime getDate() {
		return date;
	}

	/**
	 * Sets the {@link OffsetDateTime} representing the date for which the bill is requested
	 * 
	 * @param date the date to set
	 */
	public void setDate(OffsetDateTime date) {
		this.date = date;
	}

}