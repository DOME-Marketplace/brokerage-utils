package it.eng.dome.brokerage.api;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.dome.brokerage.api.page.Page;
import it.eng.dome.tmforum.tmf651.v4.ApiClient;
import it.eng.dome.tmforum.tmf651.v4.ApiException;
import it.eng.dome.tmforum.tmf651.v4.api.AgreementApi;
import it.eng.dome.tmforum.tmf651.v4.model.Agreement;
import it.eng.dome.tmforum.tmf651.v4.model.AgreementCreate;
import it.eng.dome.tmforum.tmf651.v4.model.AgreementUpdate;


public class AgreementManagementApis {
	private final Logger logger = LoggerFactory.getLogger(AgreementManagementApis.class);
	private AgreementApi agreementApi;

	/**
	 * Constructor
	 * 
	 * @param apiClientTMF651
	 */
	public AgreementManagementApis(ApiClient apiClientTMF651) {
		logger.info("Init AgreementManagementApis - apiClientTMF651 basePath: {}", apiClientTMF651.getBasePath());
		agreementApi = new AgreementApi(apiClientTMF651);
	}

	
	/**
	 * This method creates a Agreement
	 * 
	 * @param AgreementCreate - AgreementCreate object used in the creation request of the Agreement (required) 
	 * @return Agreement
	 */
	public Agreement createAgreement(AgreementCreate agreementCreate) {		
		logger.info("Create: Agreement");
		
		try {
			return agreementApi.createAgreement(agreementCreate);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	
	/**
	 * This method updates the Agreement by Id
	 * 
	 * @param agreementId - Identifier of the Agreement (required) 
	 * @param agreementUpdate - AgreementUpdate object used to update the Agreement (required) 
	 * @return boolean
	 */
	public boolean updateAgreement(String agreementId, AgreementUpdate agreementUpdate) {
		logger.info("Request: updateAgreement by id {}", agreementId);
		
		try {
			Agreement agreement = agreementApi.patchAgreement(agreementId, agreementUpdate);
			logger.info("Update successfully Agreement with id: {}", agreement.getId());
			return true;
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return false;
		}
	}
	

	/**
	 * This method retrieves a specific Agreement by ID
	 * 
	 * @param agreementId - Identifier of the Agreement (required)
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'status,name')<br> 
	 * - use fields == null to get all attributes
	 * @return Agreement
	 */
	public Agreement getAgreement(String agreementId, String fields) {
		logger.info("Request: getAgreement by id {}", agreementId);
		
		try {
			if (fields != null) {
				logger.debug("Fields required: [{}]", fields);
			}
			
			return agreementApi.retrieveAgreement(agreementId, fields);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	
	/**
	 * This method retrieves a paginated list of Agreement
	 * 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'name,status')<br>
	 * - use fields == null to get all attributes		
     * @param offset - the index of the first item to return (used for pagination)
     * @param limit - the maximum number of items to return
	 * @param filter - HashMap<K,V> to set query string params (optional)<br>  
	 * @return a {@link Page} containing a subset of Agreement
	 */
	public Page<Agreement> listAgreements(String fields, int offset, int limit, Map<String, String> filter) {
		logger.info("Request: listAgreements");
		
		try {
			
			if (filter != null && !filter.isEmpty()) {
				logger.debug("Params used in the query-string filter: {}", filter);
			}
			if (fields != null) {
				logger.debug("Fields required: [{}]", fields);
			}
			
			List<Agreement> items = agreementApi.listAgreement(fields, offset, limit, filter);
			boolean hasNext = items.size() == limit;
			
			return new Page<>(items, offset, limit, hasNext);
			
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}   
	}
	
}
