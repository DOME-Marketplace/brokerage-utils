package it.eng.dome.brokerage.api;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.dome.tmforum.tmf632.v4.ApiClient;
import it.eng.dome.tmforum.tmf632.v4.ApiException;
import it.eng.dome.tmforum.tmf632.v4.api.IndividualApi;
import it.eng.dome.tmforum.tmf632.v4.api.OrganizationApi;
import it.eng.dome.tmforum.tmf632.v4.model.Individual;
import it.eng.dome.tmforum.tmf632.v4.model.Organization;


public class APIPartyApis {
	
	private final Logger logger = LoggerFactory.getLogger(APIPartyApis.class);	
	private OrganizationApi organizationApi;
	private IndividualApi individualApi;
	
	/**
	 * Constructor 
	 * @param apiClientTMF632
	 */
	public APIPartyApis(ApiClient apiClientTMF632){
		logger.info("Init OrganizationApis -  apiClientTMF632 basePath: {}", apiClientTMF632.getBasePath());
		organizationApi = new OrganizationApi(apiClientTMF632);
		individualApi = new IndividualApi(apiClientTMF632);
	}
	
	
	/**
	 * This method retrieves a specific Organization by id
	 * 
	 * @param id - Identifier of the Organization (required) 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'name,tradingName')<br> 
	 * - use fields == null to get all attributes
	 * @return the {@link Organization} with the given id,
	 *         or {@code null} if no Organization is found
	 */
	public Organization getOrganization(String id, String fields) {	
		logger.info("Request: getOrganization by id {}", id);
		
		try {
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}
			
			return organizationApi.retrieveOrganization(id, fields);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
		
	/**
	 * This method retrieves a list of Organization
	 * 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'name,tradingName')<br> 
	 * - use fields == null to get all attributes	
     * @param offset - the index of the first item to return (used for pagination)
     * @param limit - the maximum number of items to return
	 * @param filter - HashMap<K,V> to set query string params (optional)<br>  
	 * @return a {@link List} containing a subset of Organization
	 */
	public List<Organization> listOrganizations(String fields, int offset, int limit, Map<String, String> filter) {
		logger.info("Request: listOrganizations");
		
		try {
			
			if (filter != null && !filter.isEmpty()) {
				logger.debug("Params used in the query-string filter: {}", filter);
			}
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}
			
			return organizationApi.listOrganization(fields, offset, limit, filter);
			
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}   
	}

	
	/**
	 * This method retrieves a specific Individual by id
	 * 
	 * @param id - Identifier of the Individual (required) 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'familyName,gender')<br> 
	 * - use fields == null to get all attributes
	 * @return the {@link Individual} with the given id,
	 *         or {@code null} if no Individual is found
	 */
	public Individual getIndividual(String id, String fields) {	
		logger.info("Request: getIndividual by id {}", id);
		
		try {
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}
			
			return individualApi.retrieveIndividual(id, fields);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	
	/**
	 * This method retrieves a list of Individual
	 * 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'familyName,gender')<br> 
	 * - use fields == null to get all attributes	
     * @param offset - the index of the first item to return (used for pagination)
     * @param limit - the maximum number of items to return
	 * @param filter - HashMap<K,V> to set query string params (optional)<br>  
	 * @return a {@link List} containing a subset of Individual
	 */
	public List<Individual> listIndividuals(String fields, int offset, int limit, Map<String, String> filter) {
		logger.info("Request: listIndividuals");
		
		try {
			
			if (filter != null && !filter.isEmpty()) {
				logger.debug("Params used in the query-string filter: {}", filter);
			}
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}
			
			return individualApi.listIndividual(fields, offset, limit, filter);
			
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}   
	}
}
