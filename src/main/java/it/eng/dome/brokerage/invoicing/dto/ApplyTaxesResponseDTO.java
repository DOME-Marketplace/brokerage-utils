package it.eng.dome.brokerage.invoicing.dto;

import java.util.List;

import it.eng.dome.tmforum.tmf678.v4.model.AppliedCustomerBillingRate;
import it.eng.dome.tmforum.tmf678.v4.model.CustomerBill;

/**
 * This class represents the DTO returned by the "invoicing/applyTaxes" API  to calculate the taxes of the bill \n.
 * This class contains information about the {@link CustomerBill} and the list of the {@link AppliedCustomerBillingRate} contained in the CustomerBill to with the taxes applied
 */
public class ApplyTaxesResponseDTO {
	
	private CustomerBill customerBill;
	private List<AppliedCustomerBillingRate> acbrs;
	
	/** 
	* Class constructor.
	*/
	public ApplyTaxesResponseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Class constructor specifying the {@link CustomerBill} and the list of the {@link AppliedCustomerBillingRate} with taxes
	 * @param customerBill the CustomerBill with taxes applied
	 * @param acbrs the list of AppliedCustomerBillingRate with taxes applied
	 */
	public ApplyTaxesResponseDTO(CustomerBill customerBill, List<AppliedCustomerBillingRate> acbrs) {
		super();
		this.customerBill = customerBill;
		this.acbrs = acbrs;
	}

	/**
	 * Returns the {@link CustomerBill} with taxes
	 * @return  the CustomerBill with taxes
	 */
	public CustomerBill getCustomerBill() {
		return customerBill;
	}
	
	/**
	 * Sets the {@link CustomerBill}
	 * @param customerBill the {@link CustomerBill} with taxes to set
	 */
	public void setCustomerBill(CustomerBill customerBill) {
		this.customerBill = customerBill;
	}
	
	/**
	 * Returns the list of {@link AppliedCustomerBillingRate} of the {@link CustomerBill} with taxes
	 * @return the list of AppliedCustomerBillRate of the CustomerBill with taxes
	 */
	public List<AppliedCustomerBillingRate> getAcbrs() {
		return acbrs;
	}
	
	/**
	 * Sets the list of {@link AppliedCustomerBillingRate} of the {@link CustomerBill} with taxes
	 * @param acbrs the list of AppliedCustomerBillingRate of the CustomerBill with taxes
	 */
	public void setAcbrs(List<AppliedCustomerBillingRate> acbrs) {
		this.acbrs = acbrs;
	}
	
	

}
