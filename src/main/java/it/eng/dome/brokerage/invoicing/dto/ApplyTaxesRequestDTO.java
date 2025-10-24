package it.eng.dome.brokerage.invoicing.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.eng.dome.tmforum.tmf637.v4.model.Product;
import it.eng.dome.tmforum.tmf678.v4.model.AppliedCustomerBillingRate;
import it.eng.dome.tmforum.tmf678.v4.model.CustomerBill;

/**
 * This class represents the DTO used by the "invoicing/applyTaxes" API  to calculate the taxes of the bill \n.
 * This class contains information about the {@link Product}, the {@link CustomerBill} and the list of the {@link AppliedCustomerBillingRate} contained in the CustomerBill to which the taxes must be applied
 */
public class ApplyTaxesRequestDTO {
	
	private Product product;

	private CustomerBill customerBill;
	
	private List<AppliedCustomerBillingRate> appliedCustomerBillingRate;
	
	/** 
	* Class constructor.
	*/
	public ApplyTaxesRequestDTO() {
	}
	
	/**
	 * Class constructor specifying the {@link Product} and the list of the {@link AppliedCustomerBillingRate} to which the taxes must be applied
	 */
	@JsonCreator
	public ApplyTaxesRequestDTO(@JsonProperty("product") Product pr, @JsonProperty("customerBill") CustomerBill cb, @JsonProperty("appliedCustomerBillingRate") List<AppliedCustomerBillingRate> acbrl) {
		this.setProduct(pr);
		this.setCustomerBill(cb);
		this.setAppliedCustomerBillingRate(acbrl);
	}
	
	/**
	 * Returns the {@link Product} of the {@link AppliedCustomerBillingRate}(s)
	 * 
	 * @return the Product of the AppliedCustomerBillingRate(s)
	 */
	public Product getProduct() {
		return product;
	}
	
	/**
	 * Sets the {@link Product} of the the {@link AppliedCustomerBillingRate}(s)
	 * 
	 * @param product the Product of the AppliedCustomerBillingRate(s) to set
	 */
	public void setProduct(Product product) {
		this.product = product;
	}
	
	/**
	 * Returns the {@link CustomerBill}
	 * 
	 * @return the CustomerBill
	 */
	public CustomerBill getCustomerBill() {
		return customerBill;
	}

	/**
	 * Sets the {@link CustomerBill}
	 * @param customerBill the CustomerBill to set
	 */
	public void setCustomerBill(CustomerBill customerBill) {
		this.customerBill = customerBill;
	}
	
	/**
	 * Returns the list of the {@link AppliedCustomerBillingRate} to which the taxes must be applied
	 * 
	 * @return the list of the AppliedCustomerBillingRate to which the taxes must be applied
	 */
	public List<AppliedCustomerBillingRate> getAppliedCustomerBillingRate() {
		return appliedCustomerBillingRate;
	}
	
	/**
	 * Sets the list of the {@link AppliedCustomerBillingRate} to which the taxes must be applied
	 * 
	 * @param appliedCustomerBillingRate the list of the AppliedCustomerBillingRate to set
	 */
	public void setAppliedCustomerBillingRate(List<AppliedCustomerBillingRate> appliedCustomerBillingRate) {
		this.appliedCustomerBillingRate = appliedCustomerBillingRate;
	}

}
