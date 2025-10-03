package it.eng.dome.brokerage.api;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	 * @param agreementCreate - AgreementCreate object used in the creation request of the Agreement (required) 
	 * @return the id of the created Agreement, or {@code null} if the creation failed
	 */
	public String createAgreement(AgreementCreate agreementCreate) {		
		logger.info("Create: Agreement");
		
		try {
			Agreement agreement = agreementApi.createAgreement(agreementCreate);
			logger.info("Agreement saved successfully with id: {}", agreement.getId());
			return agreement.getId();
		} catch (ApiException e) {
			logger.info("Agreement not saved: {}", agreementCreate.toString());
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	
	/**
	 * This method updates the Agreement by id
	 * 
	 * @param id - Identifier of the Agreement (required) 
	 * @param agreementUpdate - AgreementUpdate object used to update the Agreement (required) 
	 * @return {@code true} if the update was successful,
	 *         {@code false} otherwise
	 */
	public boolean updateAgreement(String id, AgreementUpdate agreementUpdate) {
		logger.info("Request: updateAgreement by id {}", id);
		
		try {
			Agreement agreement = agreementApi.patchAgreement(id, agreementUpdate);
			logger.info("Update successfully Agreement with id: {}", agreement.getId());
			return true;
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return false;
		}
	}
	

	/**
	 * This method retrieves a specific Agreement by id
	 * 
	 * @param id - Identifier of the Agreement (required)
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'status,name')<br> 
	 * - use fields == null to get all attributes
	 * @return the {@link Agreement} with the given id,
	 *         or {@code null} if no Agreement is found
	 */
	public Agreement getAgreement(String id, String fields) {
		logger.info("Request: getAgreement by id {}", id);
		
		try {
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}
			
			return agreementApi.retrieveAgreement(id, fields);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	
	/**
	 * This method retrieves a list of Agreement
	 * 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'name,status')<br>
	 * - use fields == null to get all attributes		
     * @param offset - the index of the first item to return (used for pagination)
     * @param limit - the maximum number of items to return
	 * @param filter - HashMap<K,V> to set query string params (optional)<br>  
	 * @return a {@link List} containing a subset of Agreement
	 */
	public List<Agreement> listAgreements(String fields, int offset, int limit, Map<String, String> filter) {
		logger.info("Request: listAgreements");
		
		try {
			
			if (filter != null && !filter.isEmpty()) {
				logger.debug("Params used in the query-string filter: {}", filter);
			}
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}
			
			return agreementApi.listAgreement(fields, offset, limit, filter);
			
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}   
	}
	
}
