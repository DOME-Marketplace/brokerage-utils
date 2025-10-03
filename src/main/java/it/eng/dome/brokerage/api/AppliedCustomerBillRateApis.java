package it.eng.dome.brokerage.api;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.dome.tmforum.tmf678.v4.ApiClient;
import it.eng.dome.tmforum.tmf678.v4.ApiException;
import it.eng.dome.tmforum.tmf678.v4.api.AppliedCustomerBillingRateApi;
import it.eng.dome.tmforum.tmf678.v4.model.AppliedCustomerBillingRate;
import it.eng.dome.tmforum.tmf678.v4.model.AppliedCustomerBillingRateCreate;
import it.eng.dome.tmforum.tmf678.v4.model.AppliedCustomerBillingRateUpdate;

public class AppliedCustomerBillRateApis {

	private final Logger logger = LoggerFactory.getLogger(AppliedCustomerBillRateApis.class);	
	private AppliedCustomerBillingRateApi appliedCustomerBillingRateApi;

	/**
	 * Constructor
	 * @param apiClientTMF678
	 */
	public AppliedCustomerBillRateApis(ApiClient apiClientTMF678){
		logger.info("Init AppliedCustomerBillRateApis - apiClientTMF678 basePath: {}", apiClientTMF678.getBasePath());
		appliedCustomerBillingRateApi = new AppliedCustomerBillingRateApi(apiClientTMF678);	
	}

	
	/**
	 * This method retrieves a specific AppliedCustomerBillingRate by id
	 *  
	 * @param id - Identifier of the AppliedCustomerBillingRate (required)
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'product,periodCoverage')<br> 
	 * - use fields == null to get all attributes
	 * @return the {@link AppliedCustomerBillingRate} with the given id,
	 *         or {@code null} if no AppliedCustomerBillingRate is found
	 */
	public AppliedCustomerBillingRate getAppliedCustomerBillingRate(String id, String fields) {
		logger.info("Request: getAppliedCustomerBillingRate by id {}", id);
		
		try {
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}
			
			return appliedCustomerBillingRateApi.retrieveAppliedCustomerBillingRate(id, fields);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	/**
	 * This method retrieves a list of AppliedCustomerBillingRate
	 * 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'product,periodCoverage')<br> 
	 * - use fields == null to get all attributes	
     * @param offset - the index of the first item to return (used for pagination)
     * @param limit - the maximum number of items to return
	 * @param filter - HashMap<K,V> to set query string params (optional)<br>  
	 * @return a {@link List} containing a subset of AppliedCustomerBillingRate
	 */
	public List<AppliedCustomerBillingRate> listAppliedCustomerBillingRates(String fields, int offset, int limit, Map<String, String> filter) {
		logger.info("Request: listAppliedCustomerBillingRates");
		
		try {			
			if (filter != null && !filter.isEmpty()) {
				logger.debug("Params used in the query-string filter: {}", filter);
			}
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}
			
			return appliedCustomerBillingRateApi.listAppliedCustomerBillingRate(fields, offset, limit, filter);
			
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}   
	}

	
	/**
	 * This method updates the AppliedCustomerBillingRate by id
	 * 
	 * @param id - Identifier of the AppliedCustomerBillingRate (required) 
	 * @param appliedCustomerBillingRateUpdate - AppliedCustomerBillingRateUpdate object used to update the AppliedCustomerBillingRate (required) 
	 * @return {@code true} if the update was successful,
	 *         {@code false} otherwise
	 */
	public boolean updateAppliedCustomerBillingRate(String id, AppliedCustomerBillingRateUpdate appliedCustomerBillingRateUpdate) {
		logger.info("Request: updateAppliedCustomerBillingRate by id {}", id);
		
		try {
			AppliedCustomerBillingRate billUpdate = appliedCustomerBillingRateApi.updateAppliedCustomerBillingRate(id, appliedCustomerBillingRateUpdate);
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
	 * @return the id of the created AppliedCustomerBillingRate, or {@code null} if the creation failed
	 */
	public String createAppliedCustomerBillingRate(AppliedCustomerBillingRateCreate appliedCustomerBillingRateCreate) {
		logger.info("Create: AppliedCustomerBillingRate");
		
		try {
			AppliedCustomerBillingRate applied = appliedCustomerBillingRateApi.createAppliedCustomerBillingRate(appliedCustomerBillingRateCreate);
			logger.info("AppliedCustomerBillingRate saved successfully with id: {}", applied.getId());
			return applied.getId();
		} catch (ApiException e) {
			logger.info("AppliedCustomerBillingRate not saved: {}", appliedCustomerBillingRateCreate.toString());
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
}
