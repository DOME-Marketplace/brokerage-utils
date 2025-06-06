package it.eng.dome.brokerage.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.dome.tmforum.tmf678.v4.ApiClient;
import it.eng.dome.tmforum.tmf678.v4.ApiException;
import it.eng.dome.tmforum.tmf678.v4.api.AppliedCustomerBillingRateApi;
import it.eng.dome.tmforum.tmf678.v4.api.CustomerBillExtensionApi;
import it.eng.dome.tmforum.tmf678.v4.model.AppliedCustomerBillingRate;
import it.eng.dome.tmforum.tmf678.v4.model.AppliedCustomerBillingRateCreate;
import it.eng.dome.tmforum.tmf678.v4.model.AppliedCustomerBillingRateUpdate;
import it.eng.dome.tmforum.tmf678.v4.model.CustomerBill;
import it.eng.dome.tmforum.tmf678.v4.model.CustomerBillCreate;

public class AppliedCustomerBillRateApis {
	
	private final Logger logger = LoggerFactory.getLogger(AppliedCustomerBillRateApis.class);
	private final int LIMIT = 100;
	
	private AppliedCustomerBillingRateApi appliedCustomerBillingRate;
	private CustomerBillExtensionApi customerBillExtension;

	/**
	 * Constructor
	 * @param apiClientTMF678
	 */
	public AppliedCustomerBillRateApis(ApiClient apiClientTMF678){
		logger.info("Init AppliedCustomerBillRateApis - apiClientTMF678 basePath: {}", apiClientTMF678.getBasePath());
		appliedCustomerBillingRate = new AppliedCustomerBillingRateApi(apiClientTMF678);	
		customerBillExtension = new CustomerBillExtensionApi(apiClientTMF678);
	}
	

	/**
	 * This method retrieves a specific AppliedCustomerBillingRate by ID
	 *  
	 * @param applyId - Identifier of the AppliedCustomerBillingRate (required)
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'product,periodCoverage')<br> 
	 * - use fields == null to get all attributes
	 * @return AppliedCustomerBillingRate
	 */
	public AppliedCustomerBillingRate getAppliedCustomerBillingRate(String applyId, String fields) {
		try {
			
			return appliedCustomerBillingRate.retrieveAppliedCustomerBillingRate(applyId, fields);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	/**
	 * This method retrieves the list of AppliedCustomerBillingRate
	 * 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'product,periodCoverage')<br> 
	 * - use fields == null to get all attributes	
	 * @param filter - HashMap<K,V> to set query string params (optional)<br>  
	 * @return List&lt;AppliedCustomerBillingRate&gt;
	 */
	
	public List<AppliedCustomerBillingRate> getAllAppliedCustomerBillingRates(String fields, Map<String, String> filter) {
		logger.info("Request: getAllAppliedCustomerBillingRates");
		List<AppliedCustomerBillingRate> all = new ArrayList<AppliedCustomerBillingRate>();
		
		if (filter != null && !filter.isEmpty()) {
			logger.debug("Params used in the query-string filter: {}", filter);
		}
		
		getAllApplied(all, fields, 0, filter);
		logger.info("Number of AppliedCustomerBillingRates: {}", all.size());
		return all;
	}
	
	/**
	 * This method updates the AppliedCustomerBillingRate by Id
	 * 
	 * @param appliedId - Identifier of the AppliedCustomerBillingRate (required) 
	 * @param appliedCustomerBillingRateUpdate - AppliedCustomerBillingRateUpdate object used to update the AppliedCustomerBillingRate (required) 
	 * @return boolean
	 */
	public boolean updateAppliedCustomerBillingRate(String appliedId, AppliedCustomerBillingRateUpdate appliedCustomerBillingRateUpdate) {
		logger.info("Request: updateAppliedCustomerBillingRate");
		try {
			AppliedCustomerBillingRate billUpdate = appliedCustomerBillingRate.updateAppliedCustomerBillingRate(appliedId, appliedCustomerBillingRateUpdate);
			logger.info("Update AppliedCustomerBillingRate with id: {}", billUpdate.getId());
			return true;
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return false;
		}
	}
	
	/**
	 * This method creates an AppliedCustomerBillingRate
	 * 
	 * @param appliedCustomerBillingRateCreate - AppliedCustomerBillingRateCreate object used in the creation request of the AppliedCustomerBillingRate (required) 
	 * @return AppliedCustomerBillingRate
	 */
	public AppliedCustomerBillingRate createAppliedCustomerBillingRate(AppliedCustomerBillingRateCreate appliedCustomerBillingRateCreate) {
		try {
			AppliedCustomerBillingRate applied = appliedCustomerBillingRate.createAppliedCustomerBillingRate(appliedCustomerBillingRateCreate);
			return applied;
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	/**
	 * This method creates a CustomerBill
	 * 
	 * @param customerBillCreate - CustomerBillCreate object used in the creation request of the CustomerBill (required) 
	 * @return customerBillId
	 */
	public String createCustomerBill(CustomerBillCreate customerBillCreate) {
		logger.info("Request: createCustomerBill");
		try {
			CustomerBill customerBill = customerBillExtension.createCustomerBill(customerBillCreate);
			logger.info("CustomerBill saved with id: {}", customerBill.getId());
			return customerBill.getId();
		} catch (ApiException e) {
			logger.info("CustomerBill not saved: {}", customerBillCreate.toString());
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	/*
	 * Internal method to get all AppliedCustomerBillingRate in recursive way
	 */
	private void getAllApplied(List<AppliedCustomerBillingRate> list, String fields, int start, Map<String, String> filter) {
		int offset = start * LIMIT;

		try {
			List<AppliedCustomerBillingRate> appliedList = appliedCustomerBillingRate.listAppliedCustomerBillingRate(fields, offset, LIMIT, filter);

			if (!appliedList.isEmpty()) {
				list.addAll(appliedList);
				getAllApplied(list, fields, start + 1, filter);
			}else {
				return;
			}
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return;
		}		
	}
}
