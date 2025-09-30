package it.eng.dome.brokerage.api.base;

import it.eng.dome.tmforum.tmf678.v4.ApiClient;
import it.eng.dome.tmforum.tmf678.v4.api.AppliedCustomerBillingRateApi;
import it.eng.dome.tmforum.tmf678.v4.api.CustomerBillApi;
import it.eng.dome.tmforum.tmf678.v4.api.CustomerBillExtensionApi;

public abstract class ApiClientTMF678 {
	
	protected AppliedCustomerBillingRateApi appliedCustomerBillingRate;
	protected CustomerBillExtensionApi customerBillExtension;
	protected CustomerBillApi customerBill;

	
	/**
	 * Constructor
	 * @param apiClientTMF678
	 */
	public ApiClientTMF678(ApiClient apiClientTMF678){
		appliedCustomerBillingRate = new AppliedCustomerBillingRateApi(apiClientTMF678);	
		customerBillExtension = new CustomerBillExtensionApi(apiClientTMF678);
		customerBill = new CustomerBillApi(apiClientTMF678);	
	}
	

}
