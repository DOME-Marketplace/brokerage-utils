package it.eng.dome.brokerage.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.dome.tmforum.tmf634.v4.ApiClient;
import it.eng.dome.tmforum.tmf634.v4.ApiException;
import it.eng.dome.tmforum.tmf634.v4.api.ResourceSpecificationApi;
import it.eng.dome.tmforum.tmf634.v4.model.ResourceSpecification;


public class ResourceSpecificationApis {
	
	private final Logger logger = LoggerFactory.getLogger(ResourceSpecificationApis.class);
	private final int LIMIT = 100;
	
	private ResourceSpecificationApi rsApi;
	
	/**
	 * Constructor
	 * @param apiClientTMF634
	 */
	public ResourceSpecificationApis(ApiClient apiClientTMF634){
		logger.info("Init ResourceSpecificationApis - apiClientTMF634 basePath: {}", apiClientTMF634.getBasePath());
		rsApi = new ResourceSpecificationApi(apiClientTMF634);
	}
	
	/**
	 * This method retrieves a specific ResourceSpecification by ID
	 * 
	 * @param rsId - Identifier of the ResourceSpecification (required) 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'name,description')<br> 
	 * - use fields == null to get all attributes
	 * @return ResourceSpecification
	 */
	public ResourceSpecification getResourceSpecification(String rsId, String fields) {
		try {
			return  rsApi.retrieveResourceSpecification(rsId, fields);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	
	/**
	 * This method retrieves the list of ResourceSpecification
	 * 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'name,description')<br> 
	 * - use fields == null to get all attributes
	 * @param filter - HashMap<K,V> to set query string params (optional)<br> 
	 * @return List&lt;ResourceSpecification&gt;
	 */
	public List<ResourceSpecification> getAllResourceSpecification(String fields, Map<String, String> filter) {
		logger.info("Request: getAllResourceSpecification");
		List<ResourceSpecification> all = new ArrayList<ResourceSpecification>();
		
		if (filter != null && !filter.isEmpty()) {
			logger.debug("Params used in the query-string filter: {}", filter);
		}
		
		getAllResourceSpecifications(all, fields, 0, filter);
		logger.info("Number of ResourceSpecifications: {}", all.size());
		return all;
	}
		
	private void getAllResourceSpecifications(List<ResourceSpecification> list, String fields, int start, Map<String, String> filter) {
		int offset = start * LIMIT;

		try {
			List<ResourceSpecification> productSpecificationgList = rsApi.listResourceSpecification(fields, offset, LIMIT, filter);

			if (!productSpecificationgList.isEmpty()) {
				list.addAll(productSpecificationgList);
				getAllResourceSpecifications(list, fields, start + 1, filter);				
			}else {
				return;
			}
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return;
		}		
	}
}
