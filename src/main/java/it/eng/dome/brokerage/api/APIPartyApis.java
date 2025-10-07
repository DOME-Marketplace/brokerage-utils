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
	 * Retrieves a specific {@link Organization} by its unique identifier.
	 *
	 * @param id      the identifier of the {@code Organization} to retrieve (required)
	 * @param fields  a comma-separated list of properties to include in the response (optional) <br>
	 *                - use this parameter to request specific attributes (e.g., {@code "name,tradingName"}) <br>
	 *                - use {@code null} or an empty string to retrieve all available attributes
	 * @return the {@link Organization} matching the given {@code id}
	 * @throws ApiException if the API call fails or the resource cannot be retrieved
	 */
	public Organization getOrganization(String id, String fields) throws ApiException {	
		logger.info("Request: getOrganization by id {}", id);

		if (fields != null) {
			logger.debug("Selected attributes: [{}]", fields);
		}
		
		return organizationApi.retrieveOrganization(id, fields);
	}
		
	
	/**
	 * Retrieves a list of {@link Organization} resources.
	 * <p>
	 * This method queries the API Party Management API and returns a paginated subset of results 
	 * based on the provided {@code offset}, {@code limit}, and optional filter criteria.
	 * </p>
	 *
	 * @param fields a comma-separated list of properties to include in the response (optional)<br>
	 *               - use this string to select specific fields (e.g. {@code "name,tradingName"})<br>
	 *               - use {@code null} to retrieve all attributes
	 * @param offset the index of the first item to return 
	 * @param limit  the maximum number of items to return 
	 * @param filter a {@link Map} of query parameters used for filtering results (optional)
	 * @return a {@link List} containing the retrieved {@link Organization} resources
	 * @throws ApiException if the API call fails or the resources cannot be retrieved
	 */
	public List<Organization> listOrganizations(String fields, int offset, int limit, Map<String, String> filter) throws ApiException {
		logger.info("Request: listOrganizations");
					
		if (filter != null && !filter.isEmpty()) {
			logger.debug("Params used in the query-string filter: {}", filter);
		}
		if (fields != null) {
			logger.debug("Selected attributes: [{}]", fields);
		}
		
		return organizationApi.listOrganization(fields, offset, limit, filter);
	}

	
	/**
	 * Retrieves a specific {@link Individual} by its unique identifier.
	 *
	 * @param id      the identifier of the {@code Individual} to retrieve (required)
	 * @param fields  a comma-separated list of properties to include in the response (optional) <br>
	 *                - use this parameter to request specific attributes (e.g., {@code "familyName,gender"}) <br>
	 *                - use {@code null} or an empty string to retrieve all available attributes
	 * @return the {@link Individual} matching the given {@code id}
	 * @throws ApiException if the API call fails or the resource cannot be retrieved
	 */
	public Individual getIndividual(String id, String fields) throws ApiException {	
		logger.info("Request: getIndividual by id {}", id);

		if (fields != null) {
			logger.debug("Selected attributes: [{}]", fields);
		}
		
		return individualApi.retrieveIndividual(id, fields);
	}
	
	
	/**
	 * Retrieves a list of {@link Individual} resources.
	 * <p>
	 * This method queries the API Party Management API and returns a paginated subset of results 
	 * based on the provided {@code offset}, {@code limit}, and optional filter criteria.
	 * </p>
	 *
	 * @param fields a comma-separated list of properties to include in the response (optional)<br>
	 *               - use this string to select specific fields (e.g. {@code "familyName,gender"})<br>
	 *               - use {@code null} to retrieve all attributes
	 * @param offset the index of the first item to return 
	 * @param limit  the maximum number of items to return 
	 * @param filter a {@link Map} of query parameters used for filtering results (optional)
	 * @return a {@link List} containing the retrieved {@link Individual} resources
	 * @throws ApiException if the API call fails or the resources cannot be retrieved
	 */
	public List<Individual> listIndividuals(String fields, int offset, int limit, Map<String, String> filter) throws ApiException {
		logger.info("Request: listIndividuals");
			
		if (filter != null && !filter.isEmpty()) {
			logger.debug("Params used in the query-string filter: {}", filter);
		}
		if (fields != null) {
			logger.debug("Selected attributes: [{}]", fields);
		}
		
		return individualApi.listIndividual(fields, offset, limit, filter);
	}
	
}
