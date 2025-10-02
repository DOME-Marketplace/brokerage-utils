package it.eng.dome.brokerage.api;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.dome.brokerage.api.page.Page;
import it.eng.dome.tmforum.tmf633.v4.ApiClient;
import it.eng.dome.tmforum.tmf633.v4.ApiException;
import it.eng.dome.tmforum.tmf633.v4.api.ServiceSpecificationApi;
import it.eng.dome.tmforum.tmf633.v4.model.ServiceSpecification;


public class ServiceCatalogManagementApis {

    private final Logger logger = LoggerFactory.getLogger(ServiceCatalogManagementApis.class);
    private ServiceSpecificationApi serviceSpecificationApi;
    
    /**
     * Constructor
     * @param apiClientTMF633
     */
    public ServiceCatalogManagementApis(ApiClient apiClientTMF633){
        logger.info("Init ServiceSpecificationApis - apiClientTMF633 basePath: {}", apiClientTMF633.getBasePath());
        serviceSpecificationApi = new ServiceSpecificationApi(apiClientTMF633);
    }

    
    /**
     * This method retrieves a specific ServiceSpecification by id
     *
     * @param id - Identifier of the ServiceSpecification (required)
     * @param fields - Comma-separated properties to be provided in response (optional)<br>
     * - use this string to get specific fields (separated by comma: i.e. 'name,description')<br>
     * - use fields == null to get all attributes
	 * @return the {@link ServiceSpecification} with the given id,
	 *         or {@code null} if no ServiceSpecification is found
     */
    public ServiceSpecification getServiceSpecification(String id, String fields) {
		logger.info("Request: getServiceSpecification by id {}", id);
		
		try {
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}
			
            return  serviceSpecificationApi.retrieveServiceSpecification(id, fields);
        } catch (ApiException e) {
            logger.error("Error: {}", e.getResponseBody(), e);
            return null;
        }
    }
    
    /**
	 * This method retrieves a paginated list of ServiceSpecification
	 * 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'name,description')<br>
	 * - use fields == null to get all attributes		
     * @param offset - the index of the first item to return (used for pagination)
     * @param limit - the maximum number of items to return
	 * @param filter - HashMap<K,V> to set query string params (optional)<br>  
	 * @return a {@link Page} containing a subset of ServiceSpecification
	 */
	public Page<ServiceSpecification> listServiceSpecifications(String fields, int offset, int limit, Map<String, String> filter) {
		logger.info("Request: listServiceSpecifications");
		
		try {
			
			if (filter != null && !filter.isEmpty()) {
				logger.debug("Params used in the query-string filter: {}", filter);
			}
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}
			
			List<ServiceSpecification> items = serviceSpecificationApi.listServiceSpecification(fields, offset, limit, filter);
			boolean hasNext = items.size() == limit;
			
			return new Page<>(items, offset, limit, hasNext);
			
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}   
	}

}
