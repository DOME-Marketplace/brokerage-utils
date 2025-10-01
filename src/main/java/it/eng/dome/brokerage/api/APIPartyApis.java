package it.eng.dome.brokerage.api;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.dome.brokerage.api.page.Page;
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
	 * This method retrieves a specific Organization by ID
	 * 
	 * @param organizationId - Identifier of the ProductOfferingPrice (required) 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'name,tradingName')<br> 
	 * - use fields == null to get all attributes
	 * @return Organization
	 */
	public Organization getOrganization(String organizationId, String fields) {	
		logger.info("Request: getOrganization by id {}", organizationId);
		
		try {
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}
			
			return organizationApi.retrieveOrganization(organizationId, fields);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
		
	/**
	 * This method retrieves a paginated list of Organization
	 * 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'name,tradingName')<br> 
	 * - use fields == null to get all attributes	
     * @param offset - the index of the first item to return (used for pagination)
     * @param limit - the maximum number of items to return
	 * @param filter - HashMap<K,V> to set query string params (optional)<br>  
	 * @return a {@link Page} containing a subset of Organization
	 */
	public Page<Organization> listOrganizations(String fields, int offset, int limit, Map<String, String> filter) {
		logger.info("Request: listOrganizations");
		
		try {
			
			if (filter != null && !filter.isEmpty()) {
				logger.debug("Params used in the query-string filter: {}", filter);
			}
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}
			
			List<Organization> items = organizationApi.listOrganization(fields, offset, limit, filter);
			boolean hasNext = items.size() == limit;
			
			return new Page<>(items, offset, limit, hasNext);
			
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}   
	}

	
	/**
	 * This method retrieves a specific Individual by ID
	 * 
	 * @param individualId - Identifier of the ProductOfferingPrice (required) 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'familyName,gender')<br> 
	 * - use fields == null to get all attributes
	 * @return Individual
	 */
	public Individual getIndividual(String individualId, String fields) {	
		logger.info("Request: getIndividual by id {}", individualId);
		
		try {
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}
			
			return individualApi.retrieveIndividual(individualId, fields);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	
	/**
	 * This method retrieves a paginated list of Individual
	 * 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'familyName,gender')<br> 
	 * - use fields == null to get all attributes	
     * @param offset - the index of the first item to return (used for pagination)
     * @param limit - the maximum number of items to return
	 * @param filter - HashMap<K,V> to set query string params (optional)<br>  
	 * @return a {@link Page} containing a subset of Individual
	 */
	public Page<Individual> listIndividuals(String fields, int offset, int limit, Map<String, String> filter) {
		logger.info("Request: listIndividuals");
		
		try {
			
			if (filter != null && !filter.isEmpty()) {
				logger.debug("Params used in the query-string filter: {}", filter);
			}
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}
			
			List<Individual> items = individualApi.listIndividual(fields, offset, limit, filter);
			boolean hasNext = items.size() == limit;
			
			return new Page<>(items, offset, limit, hasNext);
			
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}   
	}
}
