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
	 * This method creates a Usage
	 * 
	 * @param usageCreate - UsageCreate object used in the creation request of the Usage (required) 
	 * @return the id of the created Usage, or {@code null} if the creation failed
	 */
	public String createUsage(UsageCreate usageCreate) {	
		logger.info("Create: Usage");
		
		try {
			Usage usage = usageApi.createUsage(usageCreate);
			logger.info("Usage saved successfully with id: {}", usage.getId());
			return usage.getId();
		} catch (ApiException e) {
			logger.info("Usage not saved: {}", usageCreate.toString());
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	
	/**
	 * This method updates the Usage by id
	 * 
	 * @param id - Identifier of the Usage (required) 
	 * @param usageUpdate - UsageUpdate object used to update the Usage (required) 
	 * @return {@code true} if the update was successful,
	 *         {@code false} otherwise
	 */
	public boolean updateUsage(String id, UsageUpdate usageUpdate) {
		logger.info("Request: updateUsage by id {}", id);
		
		try {
			Usage usage = usageApi.patchUsage(id, usageUpdate);
			logger.info("Update successfully Usage with id: {}", usage.getId());
			return true;
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return false;
		}
	}
	

	/**
	 * This method retrieves a specific Usage by id
	 * 
	 * @param id - Identifier of the Usage (required)
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'status,usageType')<br> 
	 * - use fields == null to get all attributes
	 * @return the {@link Usage} with the given id,
	 *         or {@code null} if no Usage is found
	 */
	public Usage getUsage(String id, String fields) {
		logger.info("Request: getUsage by id {}", id);
		
		try {
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}
			
			return usageApi.retrieveUsage(id, fields);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	
	/**
	 * This method retrieves a list of Usage
	 * 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'status,usageType')<br> 
	 * - use fields == null to get all attributes	
     * @param offset - the index of the first item to return (used for pagination)
     * @param limit - the maximum number of items to return
	 * @param filter - HashMap<K,V> to set query string params (optional)<br>  
	 * @return a {@link List} containing a subset of Usage
	 */
	public List<Usage> listUsages(String fields, int offset, int limit, Map<String, String> filter) {
		logger.info("Request: listUsages");
		
		try {
			
			if (filter != null && !filter.isEmpty()) {
				logger.debug("Params used in the query-string filter: {}", filter);
			}
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}
			
			return usageApi.listUsage(fields, offset, limit, filter);
			
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}   
	}
	
		
	/**
	 * This method creates a UsageSpecification
	 * 
	 * @param usageSpecificationCreate - UsageSpecificationCreate object used in the creation request of the UsageSpecification (required) 
	 * @return the id of the created UsageSpecification, or {@code null} if the creation failed
	 */
	public String createUsageSpecification(UsageSpecificationCreate usageSpecificationCreate) {		
		logger.info("Create: UsageSpecification");
		
		try {
			UsageSpecification usage = usageSpecificationApi.createUsageSpecification(usageSpecificationCreate);
			logger.info("UsageSpecification saved successfully with id: {}", usage.getId());
			return usage.getId();
		} catch (ApiException e) {
			logger.info("UsageSpecification not saved: {}", usageSpecificationCreate.toString());
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	
	/**
	 * This method updates the UsageSpecification by id
	 * 
	 * @param id - Identifier of the UsageSpecification (required) 
	 * @param usageSpecificationUpdate - UsageSpecificationUpdate object used to update the UsageSpecification (required) 
	 * @return {@code true} if the update was successful,
	 *         {@code false} otherwise
	 */
	public boolean updateUsageSpecification(String id, UsageSpecificationUpdate usageSpecificationUpdate) {
		logger.info("Request: updateUsageSpecification by id {}", id);
		
		try {
			UsageSpecification usageSpecification = usageSpecificationApi.patchUsageSpecification(id, usageSpecificationUpdate);
			logger.info("Update successfully UsageSpecification with id: {}", usageSpecification.getId());
			return true;
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return false;
		}
	}
	
	
	/**
	 * This method retrieves a specific UsageSpecification by id
	 * 
	 * @param id - Identifier of the UsageSpecification (required)
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'name,version')<br> 
	 * - use fields == null to get all attributes
	 * @return the {@link UsageSpecification} with the given id,
	 *         or {@code null} if no UsageSpecification is found
	 */
	public UsageSpecification getUsageSpecification(String id, String fields) {
		logger.info("Request: getUsageSpecification by id {}", id);
		
		try {
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}
			
			return usageSpecificationApi.retrieveUsageSpecification(id, fields);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	
	
	/**
	 * This method retrieves a list of UsageSpecification
	 * 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'name,version')<br> 
	 * - use fields == null to get all attributes	
     * @param offset - the index of the first item to return (used for pagination)
     * @param limit - the maximum number of items to return
	 * @param filter - HashMap<K,V> to set query string params (optional)<br>  
	 * @return a {@link List} containing a subset of UsageSpecification
	 */
	public List<UsageSpecification> listUsageSpecifications(String fields, int offset, int limit, Map<String, String> filter) {
		logger.info("Request: listUsageSpecifications");
		
		try {
			
			if (filter != null && !filter.isEmpty()) {
				logger.debug("Params used in the query-string filter: {}", filter);
			}
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}
			
			return usageSpecificationApi.listUsageSpecification(fields, offset, limit, filter);
			
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}   
	}
	
}
