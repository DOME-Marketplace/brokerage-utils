package it.eng.dome.brokerage.billing.dto;

import java.util.List;

import it.eng.dome.tmforum.tmf678.v4.model.AppliedCustomerBillingRate;
import it.eng.dome.tmforum.tmf678.v4.model.CustomerBill;

/**
 * This class represents the response returned by the "billing/bill" API of the BillingEngine after the bill (without taxes) calculation process 
 * and returned by by the "invoicing/applyTaxes" API of the InvoicingService after the application of taxes to the bills 
 * This class contains information about the generated {@link CustomerBill} and the associated {@link AppliedCustomerBillingRate}(s)
 */
public class BillingResponseDTO {
	
	private CustomerBill customerBill;
	
	private List<AppliedCustomerBillingRate> acbr;
	
	/**
	 * Class constructor
	 * 
	 * @param customerBill the {@link CustomerBill} to be set
	 * @param acbr the list of {@link AppliedCustomerBillingRate} to be set
	 */
	public BillingResponseDTO(CustomerBill customerBill, List<AppliedCustomerBillingRate> acbr) {
		super();
		this.customerBill = customerBill;
		this.acbr = acbr;
	}

	/**
	 * Class constructor
	 */
	public BillingResponseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Returns the {@link CustomerBill} 
	 * @return the CustomerBill
	 */
	public CustomerBill getCustomerBill() {
		return customerBill;
	}

	/**
	 * The {@link CustomerBill} to be set
	 * 
	 * @param customerBill the CustomerBill to set
	 */
	public void setCustomerBill(CustomerBill customerBill) {
		this.customerBill = customerBill;
	}

	/**
	 * The list of {@link AppliedCustomerBillingRate}
	 * @return the list of AppliedCustomerBillingRate
	 */
	public List<AppliedCustomerBillingRate> getAcbr() {
		return acbr;
	}

	/**
	 * The list of {@link AppliedCustomerBillingRate} to set
	 * 
	 * @param acbr the list of AppliedCustomerBillingRate to set
	 */
	public void setAcbr(List<AppliedCustomerBillingRate> acbr) {
		this.acbr = acbr;
	}
	
	

}
