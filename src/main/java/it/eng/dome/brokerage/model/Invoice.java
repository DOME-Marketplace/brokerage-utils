package it.eng.dome.brokerage.model;

import java.util.List;

import it.eng.dome.tmforum.tmf678.v4.model.AppliedCustomerBillingRate;
import it.eng.dome.tmforum.tmf678.v4.model.CustomerBill;

/**
 * This class represents an Invoice 
 */
public class Invoice {
	
	private CustomerBill customerBill;
	
	private List<AppliedCustomerBillingRate> acbrs;
	
	public Invoice() {
		// TODO Auto-generated constructor stub
	}

	public Invoice(CustomerBill customerBill, List<AppliedCustomerBillingRate> acbrs) {
		super();
		this.customerBill = customerBill;
		this.acbrs = acbrs;
	}

	public CustomerBill getCustomerBill() {
		return customerBill;
	}

	public void setCustomerBill(CustomerBill customerBill) {
		this.customerBill = customerBill;
	}

	public List<AppliedCustomerBillingRate> getAcbrs() {
		return acbrs;
	}

	public void setAcbrs(List<AppliedCustomerBillingRate> acbrs) {
		this.acbrs = acbrs;
	}

}
