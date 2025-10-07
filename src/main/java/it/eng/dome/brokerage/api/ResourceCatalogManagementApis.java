package it.eng.dome.brokerage.api;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.dome.tmforum.tmf634.v4.ApiClient;
import it.eng.dome.tmforum.tmf634.v4.ApiException;
import it.eng.dome.tmforum.tmf634.v4.api.ResourceSpecificationApi;
import it.eng.dome.tmforum.tmf634.v4.model.ResourceSpecification;


public class ResourceCatalogManagementApis {
	
	private final Logger logger = LoggerFactory.getLogger(ResourceCatalogManagementApis.class);
	private ResourceSpecificationApi resourceSpecificationApi;
	
	/**
	 * Constructor
	 * @param apiClientTMF634
	 */
	public ResourceCatalogManagementApis(ApiClient apiClientTMF634){
		logger.info("Init ResourceSpecificationApis - apiClientTMF634 basePath: {}", apiClientTMF634.getBasePath());
		resourceSpecificationApi = new ResourceSpecificationApi(apiClientTMF634);
	}
	
	
	/**
	 * Retrieves a specific {@link ResourceSpecification} by its unique identifier.
	 *
	 * @param id      the identifier of the {@code ResourceSpecification} to retrieve (required)
	 * @param fields  a comma-separated list of properties to include in the response (optional) <br>
	 *                - use this parameter to request specific attributes (e.g., {@code "name,description"}) <br>
	 *                - use {@code null} or an empty string to retrieve all available attributes
	 * @return the {@link ResourceSpecification} matching the given {@code id}
	 * @throws ApiException if the API call fails or the resource cannot be retrieved
	 */
	public ResourceSpecification getResourceSpecification(String id, String fields) throws ApiException {
		logger.info("Request: getResourceSpecification by id {}", id);

		if (fields != null) {
			logger.debug("Selected attributes: [{}]", fields);
		}
		
		return resourceSpecificationApi.retrieveResourceSpecification(id, fields);
	}
	
	
	/**
	 * Retrieves a list of {@link ResourceSpecification} resources.
	 * <p>
	 * This method queries the ResourceSpecification API and returns a paginated subset of results 
	 * based on the provided {@code offset}, {@code limit}, and optional filter criteria.
	 * </p>
	 *
	 * @param fields a comma-separated list of properties to include in the response (optional)<br>
	 *               - use this string to select specific fields (e.g. {@code "name,description"})<br>
	 *               - use {@code null} to retrieve all attributes
	 * @param offset the index of the first item to return 
	 * @param limit  the maximum number of items to return 
	 * @param filter a {@link Map} of query parameters used for filtering results (optional)
	 * @return a {@link List} containing the retrieved {@link ResourceSpecification} resources
	 * @throws ApiException if the API call fails or the resources cannot be retrieved
	 */
	public List<ResourceSpecification> listResourceSpecifications(String fields, int offset, int limit, Map<String, String> filter) throws ApiException {
		logger.info("Request: listResourceSpecifications");
					
		if (filter != null && !filter.isEmpty()) {
			logger.debug("Params used in the query-string filter: {}", filter);
		}
		if (fields != null) {
			logger.debug("Selected attributes: [{}]", fields);
		}
		
		return resourceSpecificationApi.listResourceSpecification(fields, offset, limit, filter);
	}
	
}
