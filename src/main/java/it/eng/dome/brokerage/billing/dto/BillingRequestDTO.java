package it.eng.dome.brokerage.billing.dto;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.eng.dome.tmforum.tmf637.v4.model.Product;
import it.eng.dome.tmforum.tmf637.v4.model.ProductPrice;
import it.eng.dome.tmforum.tmf678.v4.model.TimePeriod;

/**
 * This class represents the DTO used by the "billing/bill" API  to calculate a bill \n.
 * This class contains information about the Product (TMF637-v4), the TimePeriod (TMF678-v4) and the list of ProductPrice (TMF637-v4) for which the bill must be calculated.
 */
public class BillingRequestDTO {
	
	private Product product;
	private TimePeriod timePeriod;
	private List<ProductPrice> productPrice;
	
	/** 
	* Class constructor.
	*/
	public BillingRequestDTO(){}
	
	/**
	 * Class constructor specifying the Product, the TimePeriod and the list of ProductPrice for which the bill will be calculated
	 */
	@JsonCreator
	public BillingRequestDTO(@JsonProperty("product") Product pr, @JsonProperty("timePeriod") TimePeriod tp, @JsonProperty("productPrice") List<ProductPrice> ppl) {
		this.setProduct(pr);
		this.setTimePeriod(tp);
		this.setProductPrice(ppl);
	}

	/**
	 * Returns the Product to which the bill refers to
	 * 
	 * @return The Product of the bill 
	 */
	public Product getProduct() {
		return product;
	}

	/**
	 * Sets the Product of the bill
	 * 
	 * @param product The Product of the bill
	 */
	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * Returns the TimePeriod to which the bill refers to
	 * 
	 * @return The TimePeriod of the bill
	 */ 
	public TimePeriod getTimePeriod() {
		return timePeriod;
	}

	/**
	 * Sets the TimePeriod of the bill
	 * 
	 * @param timePeriod The TimePeriod of the bill
	 */
	public void setTimePeriod(TimePeriod timePeriod) {
		this.timePeriod = timePeriod;
	}

	/**
	 * Returns the list of the ProductPrice to which the bill refers to
	 * 
	 * @return The list of the ProductPrice of the bill
	 */
	public List<ProductPrice> getProductPrice() {
		return productPrice;
	}

	/**
	 * Sets the ProductPrice list of the bill
	 * 
	 * @param productPrice The ProductPrice list of the bill
	 */
	public void setProductPrice(List<ProductPrice> productPrice) {
		this.productPrice = productPrice;
	}

	
}