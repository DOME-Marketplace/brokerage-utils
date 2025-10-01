package it.eng.dome.brokerage.api;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.dome.brokerage.api.page.Page;
import it.eng.dome.tmforum.tmf678.v4.ApiClient;
import it.eng.dome.tmforum.tmf678.v4.ApiException;
import it.eng.dome.tmforum.tmf678.v4.api.AppliedCustomerBillingRateApi;
import it.eng.dome.tmforum.tmf678.v4.model.AppliedCustomerBillingRate;
import it.eng.dome.tmforum.tmf678.v4.model.AppliedCustomerBillingRateCreate;
import it.eng.dome.tmforum.tmf678.v4.model.AppliedCustomerBillingRateUpdate;

public class AppliedCustomerBillRateApis {

	private final Logger logger = LoggerFactory.getLogger(AppliedCustomerBillRateApis.class);	
	private AppliedCustomerBillingRateApi appliedCustomerBillingRate;

	/**
	 * Constructor
	 * @param apiClientTMF678
	 */
	public AppliedCustomerBillRateApis(ApiClient apiClientTMF678){
		logger.info("Init AppliedCustomerBillRateApis - apiClientTMF678 basePath: {}", apiClientTMF678.getBasePath());
		appliedCustomerBillingRate = new AppliedCustomerBillingRateApi(apiClientTMF678);	
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
		logger.info("Request: getAppliedCustomerBillingRate by id {}", applyId);
		
		try {
			if (fields != null) {
				logger.debug("Fields required: [{}]", fields);
			}
			
			return appliedCustomerBillingRate.retrieveAppliedCustomerBillingRate(applyId, fields);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	/**
	 * This method retrieves a paginated list of AppliedCustomerBillingRate
	 * 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'product,periodCoverage')<br> 
	 * - use fields == null to get all attributes	
     * @param offset - the index of the first item to return (used for pagination)
     * @param limit - the maximum number of items to return
	 * @param filter - HashMap<K,V> to set query string params (optional)<br>  
	 * @return a {@link Page} containing a subset of AppliedCustomerBillingRate
	 */
	public Page<AppliedCustomerBillingRate> listAppliedCustomerBillingRates(String fields, int offset, int limit, Map<String, String> filter) {
		logger.info("Request: listAppliedCustomerBillingRatePage");
		
		try {
			
			if (filter != null && !filter.isEmpty()) {
				logger.debug("Params used in the query-string filter: {}", filter);
			}
			if (fields != null) {
				logger.debug("Fields required: [{}]", fields);
			}
			
			List<AppliedCustomerBillingRate> items = appliedCustomerBillingRate.listAppliedCustomerBillingRate(fields, offset, limit, filter);
			boolean hasNext = items.size() == limit;
			
			return new Page<>(items, offset, limit, hasNext);
			
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}   
	}

	
	/**
	 * This method updates the AppliedCustomerBillingRate by Id
	 * 
	 * @param appliedId - Identifier of the AppliedCustomerBillingRate (required) 
	 * @param appliedCustomerBillingRateUpdate - AppliedCustomerBillingRateUpdate object used to update the AppliedCustomerBillingRate (required) 
	 * @return boolean
	 */
	public boolean updateAppliedCustomerBillingRate(String appliedId, AppliedCustomerBillingRateUpdate appliedCustomerBillingRateUpdate) {
		logger.info("Request: updateAppliedCustomerBillingRate by id {}", appliedId);
		
		try {
			AppliedCustomerBillingRate billUpdate = appliedCustomerBillingRate.updateAppliedCustomerBillingRate(appliedId, appliedCustomerBillingRateUpdate);
			logger.info("Update successfully AppliedCustomerBillingRate with id: {}", billUpdate.getId());
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
	 * @return appliedId
	 */
	public String createAppliedCustomerBillingRate(AppliedCustomerBillingRateCreate appliedCustomerBillingRateCreate) {
		logger.info("Create: AppliedCustomerBillingRate");
		
		try {
			AppliedCustomerBillingRate applied = appliedCustomerBillingRate.createAppliedCustomerBillingRate(appliedCustomerBillingRateCreate);
			logger.info("AppliedCustomerBillingRate saved successfully with id: {}", applied.getId());
			return applied.getId();
		} catch (ApiException e) {
			logger.info("AppliedCustomerBillingRate not saved: {}", appliedCustomerBillingRateCreate.toString());
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
}
