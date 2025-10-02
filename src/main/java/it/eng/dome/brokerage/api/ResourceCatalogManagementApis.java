package it.eng.dome.brokerage.api;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.dome.brokerage.api.page.Page;
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
	 * This method retrieves a specific ResourceSpecification by ID
	 * 
	 * @param resourceSpecificationId - Identifier of the ResourceSpecification (required) 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'name,description')<br> 
	 * - use fields == null to get all attributes
	 * @return ResourceSpecification
	 */
	public ResourceSpecification getResourceSpecification(String resourceSpecificationId, String fields) {
		logger.info("Request: getResourceSpecification by id {}", resourceSpecificationId);
		
		try {
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}
			
			return  resourceSpecificationApi.retrieveResourceSpecification(resourceSpecificationId, fields);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	
	/**
	 * This method retrieves a paginated list of ResourceSpecification
	 * 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'name,description')<br>
	 * - use fields == null to get all attributes		
     * @param offset - the index of the first item to return (used for pagination)
     * @param limit - the maximum number of items to return
	 * @param filter - HashMap<K,V> to set query string params (optional)<br>  
	 * @return a {@link Page} containing a subset of ResourceSpecification
	 */
	public Page<ResourceSpecification> listResourceSpecifications(String fields, int offset, int limit, Map<String, String> filter) {
		logger.info("Request: listResourceSpecifications");
		
		try {
			
			if (filter != null && !filter.isEmpty()) {
				logger.debug("Params used in the query-string filter: {}", filter);
			}
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}
			
			List<ResourceSpecification> items = resourceSpecificationApi.listResourceSpecification(fields, offset, limit, filter);
			boolean hasNext = items.size() == limit;
			
			return new Page<>(items, offset, limit, hasNext);
			
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}   
	}
	
}
