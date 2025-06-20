package it.eng.dome.brokerage.api;

import java.util.ArrayList;
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
	private final int LIMIT = 100;

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
		logger.info("Request: updateAgreement");
		try {
			Agreement agreement = agreementApi.patchAgreement(agreementId, agreementUpdate);
			logger.info("Update Agreement with id: {}", agreement.getId());
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
		try {
			return agreementApi.retrieveAgreement(agreementId, fields);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	/**
	 * This method retrieves the list of Agreement
	 * 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'status,name')<br> 
	 * - use fields == null to get all attributes
	 * @param filter - HashMap<K,V> to set query string params (optional)<br> 
	 * @return List&lt;Agreement&gt;
	 */
	public List<Agreement> getAllAgreements(String fields, Map<String, String> filter) {
		logger.info("Request: getAllAgreements");
		List<Agreement> all = new ArrayList<Agreement>();
		
		if (filter != null && !filter.isEmpty()) {
			logger.debug("Params used in the query-string filter: {}", filter);
		}
		
		getAllAgreements(all, fields, 0, filter);
		logger.info("Number of Agreements: {}", all.size());
		return all;
	}
		
	private void getAllAgreements(List<Agreement> list, String fields, int start, Map<String, String> filter) {
		int offset = start * LIMIT;

		try {
			List<Agreement> agreementList =  agreementApi.listAgreement(fields, offset, LIMIT, filter);

			if (!agreementList.isEmpty()) {
				list.addAll(agreementList);
				getAllAgreements(list, fields, start + 1, filter);				
			}else {
				return;
			}
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return;
		}		
	}
}
