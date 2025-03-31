package it.eng.dome.brokerage.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.dome.tmforum.tmf632.v4.ApiClient;
import it.eng.dome.tmforum.tmf632.v4.ApiException;
import it.eng.dome.tmforum.tmf632.v4.api.OrganizationApi;
import it.eng.dome.tmforum.tmf632.v4.model.Organization;


public class OrganizationApis {
	
	private final Logger logger = LoggerFactory.getLogger(OrganizationApis.class);
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
	 * - use this string to get specific fields (separated by comma: i.e. 'product,periodCoverage')<br> 
	 * - use fields == null to get all attributes
	 * @return Organization
	 */
	public Organization getOrganization(String organizationId, String fields) {		
		try {
			return organizationApi.retrieveOrganization(organizationId, fields);
		} catch (ApiException e) {
			logger.error("Error: ", e.getMessage());
			return null;
		}
	}

}
