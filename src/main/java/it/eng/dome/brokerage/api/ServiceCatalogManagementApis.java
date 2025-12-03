package it.eng.dome.brokerage.api;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        logger.info("Init ServiceCatalogManagementApis - apiClientTMF633 basePath: {}", apiClientTMF633.getBasePath());
        serviceSpecificationApi = new ServiceSpecificationApi(apiClientTMF633);
    }

       
	/**
	 * Retrieves a specific {@link ServiceSpecification} by its unique identifier.
	 *
	 * @param id      the identifier of the {@code ServiceSpecification} to retrieve (required)
	 * @param fields  a comma-separated list of properties to include in the response (optional) <br>
	 *                - use this parameter to request specific attributes (e.g., {@code "name,description"}) <br>
	 *                - use {@code null} or an empty string to retrieve all available attributes
	 * @return the {@link ServiceSpecification} matching the given {@code id}
	 * @throws ApiException if the API call fails or the resource cannot be retrieved
	 */
    public ServiceSpecification getServiceSpecification(String id, String fields) throws ApiException {
		logger.debug("Request: getServiceSpecification by id {}", id);

		if (fields != null) {
			logger.debug("Selected attributes: [{}]", fields);
		}
		
        return serviceSpecificationApi.retrieveServiceSpecification(id, fields);
    }
    

	/**
	 * Retrieves a list of {@link ServiceSpecification} resources.
	 * <p>
	 * This method queries the Service Catalog Management API and returns a paginated subset of results 
	 * based on the provided {@code offset}, {@code limit}, and optional filter criteria.
	 * </p>
	 *
	 * @param fields a comma-separated list of properties to include in the response (optional)<br>
	 *               - use this string to select specific fields (e.g. {@code "name,description"})<br>
	 *               - use {@code null} to retrieve all attributes
	 * @param offset the index of the first item to return 
	 * @param limit  the maximum number of items to return 
	 * @param filter a {@link Map} of query parameters used for filtering results (optional)
	 * @return a {@link List} containing the retrieved {@link ServiceSpecification} resources
	 * @throws ApiException if the API call fails or the resources cannot be retrieved
	 */
	public List<ServiceSpecification> listServiceSpecifications(String fields, int offset, int limit, Map<String, String> filter) throws ApiException {
		logger.debug("Request: listServiceSpecifications: offset={}, limit={}", offset, limit);
					
		if (filter != null && !filter.isEmpty()) {
			logger.debug("Params used in the query-string filter: {}", filter);
		}
		if (fields != null) {
			logger.debug("Selected attributes: [{}]", fields);
		}
		
		return serviceSpecificationApi.listServiceSpecification(fields, offset, limit, filter);  
	}

}
