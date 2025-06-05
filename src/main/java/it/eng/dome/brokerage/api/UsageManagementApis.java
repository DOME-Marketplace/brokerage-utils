package it.eng.dome.brokerage.api;

import java.util.ArrayList;
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
	private final int LIMIT = 100;
	
	private UsageApi usageApi;
	private UsageSpecificationApi usageSpecificationApi;
	
	/**
	 * Constructor
	 * @param apiClientTMF635
	 */
	public UsageManagementApis(ApiClient apiClientTMF635){
		logger.info("Init UsageApis - apiClientTMF635 basePath: {}", apiClientTMF635.getBasePath());
		usageApi = new UsageApi(apiClientTMF635);
		usageSpecificationApi = new UsageSpecificationApi(apiClientTMF635);

	}
	
		
	/**
	 * This method creates an Usage
	 * 
	 * @param UsageCreate - UsageCreate object used in the creation request of the Usage (required) 
	 * @return Usage
	 */
	public Usage createUsage(UsageCreate usageCreate) {		
		try {
			return usageApi.createUsage(usageCreate);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	
	/**
	 * This method updates the Usage by Id
	 * 
	 * @param usageId - Identifier of the Usage (required) 
	 * @param usageUpdate - UsageUpdate object used to update the Usage (required) 
	 * @return boolean
	 */
	public boolean updateUsage(String usageId, UsageUpdate usageUpdate) {
		logger.info("Request: updateUsage");
		try {
			Usage usage = usageApi.patchUsage(usageId, usageUpdate);
			logger.info("Update Usage with id: {}", usage.getId());
			return true;
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return false;
		}
	}

	/**
	 * This method retrieves a specific Usage by ID
	 * 
	 * @param usageId - Identifier of the Usage (required)
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'status,usageType')<br> 
	 * - use fields == null to get all attributes
	 * @return Usage
	 */
	public Usage getUsage(String usageId, String fields) {
		try {
			return usageApi.retrieveUsage(usageId, fields);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	/**
	 * This method retrieves the list of Usage
	 * 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'status,usageType')<br> 
	 * - use fields == null to get all attributes
	 * @param filter - HashMap<K,V> to set query string params (optional)<br> 
	 * @return List&lt;Usage&gt;
	 */
	public List<Usage> getAllUsages(String fields, Map<String, String> filter) {
		logger.info("Request: getAllUsages");
		List<Usage> all = new ArrayList<Usage>();
		
		if (filter != null && !filter.isEmpty()) {
			logger.debug("Params used in the query-string filter: {}", filter);
		}
		
		getAllUsages(all, fields, 0, filter);
		logger.info("Number of Usages: {}", all.size());
		return all;
	}
		
	private void getAllUsages(List<Usage> list, String fields, int start, Map<String, String> filter) {
		int offset = start * LIMIT;

		try {
			List<Usage> usageList =  usageApi.listUsage(fields, offset, LIMIT, filter);

			if (!usageList.isEmpty()) {
				list.addAll(usageList);
				getAllUsages(list, fields, start + 1, filter);				
			}else {
				return;
			}
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return;
		}		
	}
	
	
	/**
	 * This method creates an UsageSpecification
	 * 
	 * @param UsageCreate - UsageSpecificationCreate object used in the creation request of the UsageSpecification (required) 
	 * @return UsageSpecification
	 */
	public UsageSpecification createUsageSpecification(UsageSpecificationCreate usageSpecificationCreate) {		
		try {
			return usageSpecificationApi.createUsageSpecification(usageSpecificationCreate);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	/**
	 * This method updates the UsageSpecification by Id
	 * 
	 * @param specificationId - Identifier of the UsageSpecification (required) 
	 * @param usageSpecificationUpdate - UsageSpecificationUpdate object used to update the UsageSpecification (required) 
	 * @return boolean
	 */
	public boolean updateUsageSpecification(String specificationId, UsageSpecificationUpdate usageSpecificationUpdate) {
		logger.info("Request: updateUsageSpecification");
		try {
			UsageSpecification usageSpecification = usageSpecificationApi.patchUsageSpecification(specificationId, usageSpecificationUpdate);
			logger.info("Update UsageSpecification with id: {}", usageSpecification.getId());
			return true;
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return false;
		}
	}
	
	/**
	 * This method retrieves a specific UsageSpecification by ID
	 * 
	 * @param specificationId - Identifier of the UsageSpecification (required)
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'name,version')<br> 
	 * - use fields == null to get all attributes
	 * @return UsageSpecification
	 */
	public UsageSpecification getUsageSpecification(String specificationId, String fields) {
		try {
			return usageSpecificationApi.retrieveUsageSpecification(specificationId, fields);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	/**
	 * This method retrieves the list of UsageSpecification
	 * 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'name,version')<br> 
	 * - use fields == null to get all attributes
	 * @param filter - HashMap<K,V> to set query string params (optional)<br> 
	 * @return List&lt;UsageSpecification&gt;
	 */
	public List<UsageSpecification> getAllUsageSpecifications(String fields, Map<String, String> filter) {
		logger.info("Request: getAllUsageSpecifications");
		
		List<UsageSpecification> all = new ArrayList<UsageSpecification>();
		
		if (filter != null && !filter.isEmpty()) {
			logger.debug("Params used in the query-string filter: {}", filter);
		}
		
		getAllUsageSpecifications(all, fields, 0, filter);
		logger.info("Number of UsageSpecifications: {}", all.size());
		return all;
	}
	
	private void getAllUsageSpecifications(List<UsageSpecification> list, String fields, int start, Map<String, String> filter) {
		int offset = start * LIMIT;

		try {
			List<UsageSpecification> specificationList =  usageSpecificationApi.listUsageSpecification(fields, offset, LIMIT, filter);

			if (!specificationList.isEmpty()) {
				list.addAll(specificationList);
				getAllUsageSpecifications(list, fields, start + 1, filter);				
			}else {
				return;
			}
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return;
		}		
	}
}
