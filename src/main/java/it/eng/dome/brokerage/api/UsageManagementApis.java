package it.eng.dome.brokerage.api;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.dome.tmforum.tmf635.v4.ApiClient;
import it.eng.dome.tmforum.tmf635.v4.ApiException;
import it.eng.dome.tmforum.tmf635.v4.api.UsageApi;
import it.eng.dome.tmforum.tmf635.v4.api.UsageSpecificationApi;
import it.eng.dome.tmforum.tmf635.v4.model.Usage;
import it.eng.dome.tmforum.tmf635.v4.model.UsageCreate;
import it.eng.dome.tmforum.tmf635.v4.model.UsageSpecification;
import it.eng.dome.tmforum.tmf635.v4.model.UsageSpecificationCreate;
import it.eng.dome.tmforum.tmf635.v4.model.UsageSpecificationUpdate;
import it.eng.dome.tmforum.tmf635.v4.model.UsageUpdate;


public class UsageManagementApis {
	
	private final Logger logger = LoggerFactory.getLogger(UsageManagementApis.class);
	private UsageApi usageApi;
	private UsageSpecificationApi usageSpecificationApi;
	
	/**
	 * Constructor
	 * @param apiClientTMF635
	 */
	public UsageManagementApis(ApiClient apiClientTMF635){
		logger.info("Init UsageManagementApis - apiClientTMF635 basePath: {}", apiClientTMF635.getBasePath());
		usageApi = new UsageApi(apiClientTMF635);
		usageSpecificationApi = new UsageSpecificationApi(apiClientTMF635);

	}
	
	
	/**
	 * Retrieves a specific {@link Usage} by its unique identifier.
	 *
	 * @param id      the identifier of the {@code Usage} to retrieve (required)
	 * @param fields  a comma-separated list of properties to include in the response (optional) <br>
	 *                - use this parameter to request specific attributes (e.g., {@code "status,usageType"}) <br>
	 *                - use {@code null} or an empty string to retrieve all available attributes
	 * @return the {@link Usage} matching the given {@code id}
	 * @throws ApiException if the API call fails or the resource cannot be retrieved
	 */
	public Usage getUsage(String id, String fields) throws ApiException {
		logger.info("Request: getUsage by id {}", id);

		if (fields != null) {
			logger.debug("Selected attributes: [{}]", fields);
		}
		
		return usageApi.retrieveUsage(id, fields);
	}
		

	/**
	 * Retrieves a list of {@link Usage} resources.
	 * <p>
	 * This method queries the Usage API and returns a paginated subset of results 
	 * based on the provided {@code offset}, {@code limit}, and optional filter criteria.
	 * </p>
	 *
	 * @param fields a comma-separated list of properties to include in the response (optional)<br>
	 *               - use this string to select specific fields (e.g. {@code "status,usageType"})<br>
	 *               - use {@code null} to retrieve all attributes
	 * @param offset the index of the first item to return 
	 * @param limit  the maximum number of items to return 
	 * @param filter a {@link Map} of query parameters used for filtering results (optional)
	 * @return a {@link List} containing the retrieved {@link Usage} resources
	 * @throws ApiException if the API call fails or the resources cannot be retrieved
	 */
	public List<Usage> listUsages(String fields, int offset, int limit, Map<String, String> filter) throws ApiException {
		logger.info("Request: listUsages");
			
		if (filter != null && !filter.isEmpty()) {
			logger.debug("Params used in the query-string filter: {}", filter);
		}
		if (fields != null) {
			logger.debug("Selected attributes: [{}]", fields);
		}
		
		return usageApi.listUsage(fields, offset, limit, filter);
	}
	
	
	/**
	 * Creates a new {@link Usage} resource.
	 * <p>
	 * This method sends a creation request to the Billing Management API using
	 * the provided {@link UsageCreate} payload.
	 * If the creation is successful, it returns the identifier of the newly created resource.
	 * </p>
	 * 
	 * @param usageCreate the {@link UsageCreate} object used to create the new Usage (required)
	 * @return the unique identifier ({@code id}) of the created {@link Usage}
	 * @throws ApiException if the API call fails or the resource cannot be retrieved  
	 */
	public String createUsage(UsageCreate usageCreate) throws ApiException {	
		logger.info("Create: Usage");

		Usage usage = usageApi.createUsage(usageCreate);
		logger.info("Usage saved successfully with id: {}", usage.getId());
		
		return usage.getId();
	}
	
	
	/**
	 * This method updates the Usage by id
	 * 
	 * @param id - Identifier of the Usage (required) 
	 * @param usageUpdate - UsageUpdate object used to update the Usage (required) 
	 * @throws ApiException if the API call fails or the resource cannot be retrieved
	 */
	public void updateUsage(String id, UsageUpdate usageUpdate) throws ApiException {
		logger.info("Request: updateUsage by id {}", id);
		
		Usage usage = usageApi.patchUsage(id, usageUpdate);
		
		boolean success = (usage != null && usage.getId() != null);
		if (success) {
			logger.debug("Successfully updated Usage with id: {}", id);
		} else {
			logger.warn("Update may have failed for Usage id: {}", id);
		}
	}
	
	
	/**
	 * Retrieves a specific {@link UsageSpecification} by its unique identifier.
	 *
	 * @param id      the identifier of the {@code UsageSpecification} to retrieve (required)
	 * @param fields  a comma-separated list of properties to include in the response (optional) <br>
	 *                - use this parameter to request specific attributes (e.g., {@code "name,version"}) <br>
	 *                - use {@code null} or an empty string to retrieve all available attributes
	 * @return the {@link UsageSpecification} matching the given {@code id}
	 * @throws ApiException if the API call fails or the resource cannot be retrieved
	 */
	public UsageSpecification getUsageSpecification(String id, String fields) throws ApiException {
		logger.info("Request: getUsageSpecification by id {}", id);

		if (fields != null) {
			logger.debug("Selected attributes: [{}]", fields);
		}
		
		return usageSpecificationApi.retrieveUsageSpecification(id, fields);
	}
	
	
	/**
	 * Retrieves a list of {@link UsageSpecification} resources.
	 * <p>
	 * This method queries the UsageSpecification API and returns a paginated subset of results 
	 * based on the provided {@code offset}, {@code limit}, and optional filter criteria.
	 * </p>
	 *
	 * @param fields a comma-separated list of properties to include in the response (optional)<br>
	 *               - use this string to select specific fields (e.g. {@code "name,version"})<br>
	 *               - use {@code null} to retrieve all attributes
	 * @param offset the index of the first item to return 
	 * @param limit  the maximum number of items to return 
	 * @param filter a {@link Map} of query parameters used for filtering results (optional)
	 * @return a {@link List} containing the retrieved {@link UsageSpecification} resources
	 * @throws ApiException if the API call fails or the resources cannot be retrieved
	 */
	public List<UsageSpecification> listUsageSpecifications(String fields, int offset, int limit, Map<String, String> filter) throws ApiException {
		logger.info("Request: listUsageSpecifications");
				
		if (filter != null && !filter.isEmpty()) {
			logger.debug("Params used in the query-string filter: {}", filter);
		}
		if (fields != null) {
			logger.debug("Selected attributes: [{}]", fields);
		}
		
		return usageSpecificationApi.listUsageSpecification(fields, offset, limit, filter);   
	}
	

	/**
	 * Creates a new {@link UsageSpecification} resource.
	 * <p>
	 * This method sends a creation request to the Billing Management API using
	 * the provided {@link UsageSpecificationCreate} payload.
	 * If the creation is successful, it returns the identifier of the newly created resource.
	 * </p>
	 * 
	 * @param usageSpecificationCreate the {@link UsageSpecificationCreate} object used to create the new UsageSpecification (required)
	 * @return the unique identifier ({@code id}) of the created {@link UsageSpecification}
	 * @throws ApiException if the API call fails or the resource cannot be retrieved  
	 */
	public String createUsageSpecification(UsageSpecificationCreate usageSpecificationCreate) throws ApiException {		
		logger.info("Create: UsageSpecification");
		
		UsageSpecification usage = usageSpecificationApi.createUsageSpecification(usageSpecificationCreate);
		logger.info("UsageSpecification saved successfully with id: {}", usage.getId());
		
		return usage.getId();
	}
	
	
	/**
	 * This method updates the UsageSpecification by id
	 * 
	 * @param id - Identifier of the UsageSpecification (required) 
	 * @param usageSpecificationUpdate - UsageSpecificationUpdate object used to update the UsageSpecification (required) 
	 * @throws ApiException if the API call fails or the resource cannot be retrieved
	 */
	public void updateUsageSpecification(String id, UsageSpecificationUpdate usageSpecificationUpdate) throws ApiException {
		logger.info("Request: updateUsageSpecification by id {}", id);

		UsageSpecification usageSpecification = usageSpecificationApi.patchUsageSpecification(id, usageSpecificationUpdate);
		
		boolean success = (usageSpecification != null && usageSpecification.getId() != null);
		if (success) {
			logger.debug("Successfully updated UsageSpecification with id: {}", id);
		} else {
			logger.warn("Update may have failed for UsageSpecification id: {}", id);
		}
	}
	
}
