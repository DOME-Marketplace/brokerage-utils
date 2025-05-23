package it.eng.dome.brokerage.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.dome.tmforum.tmf632.v4.ApiClient;
import it.eng.dome.tmforum.tmf632.v4.ApiException;
import it.eng.dome.tmforum.tmf632.v4.api.OrganizationApi;
import it.eng.dome.tmforum.tmf632.v4.model.Organization;


public class OrganizationApis {
	
	private final Logger logger = LoggerFactory.getLogger(OrganizationApis.class);
	private final int LIMIT = 100;
	
	private OrganizationApi organizationApi;
	
	/**
	 * Constructor 
	 * @param apiClientTMF632
	 */
	public OrganizationApis(ApiClient apiClientTMF632){
		logger.info("Init OrganizationApis -  apiClientTMF632 basePath: {}", apiClientTMF632.getBasePath());
		organizationApi = new OrganizationApi(apiClientTMF632);
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
		try {
			return organizationApi.retrieveOrganization(organizationId, fields);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	
	/**
	 * This method retrieves the list of Organization
	 * 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'product,periodCoverage')<br> 
	 * - use fields == null to get all attributes	
	 * @param filter - HashMap<K,V> to set query string params (optional)<br>  
	 * @return List&lt;Organization&gt;
	 */
	
	public List<Organization> getOrganizations(String fields, Map<String, String> filter) {
		logger.info("Request: getOrganizations");
		
		if (filter != null && !filter.isEmpty()) {
			logger.debug("Params used in the query-string filter: {}", filter);
		}
		
		try {			
			List<Organization> organizations = organizationApi.listOrganization(fields, 0, LIMIT, filter);
			logger.info("Number of Organizations: {}", organizations.size());
			return organizations;
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return new ArrayList<Organization>();
		}		
	}

}
