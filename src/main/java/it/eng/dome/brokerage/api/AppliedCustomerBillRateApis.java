package it.eng.dome.brokerage.api;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.dome.brokerage.api.config.DomeTmfSchemaConfig;
import it.eng.dome.brokerage.billing.utils.DateTimeUtils;
import it.eng.dome.tmforum.tmf678.v4.ApiClient;
import it.eng.dome.tmforum.tmf678.v4.ApiException;
import it.eng.dome.tmforum.tmf678.v4.api.AppliedCustomerBillingRateApi;
import it.eng.dome.tmforum.tmf678.v4.model.AppliedCustomerBillingRate;
import it.eng.dome.tmforum.tmf678.v4.model.AppliedCustomerBillingRateCreate;
import it.eng.dome.tmforum.tmf678.v4.model.AppliedCustomerBillingRateUpdate;

public class AppliedCustomerBillRateApis {

	private final Logger logger = LoggerFactory.getLogger(AppliedCustomerBillRateApis.class);	
	private AppliedCustomerBillingRateApi appliedCustomerBillingRateApi;
	
	private final String appliedSchemaLocation = DomeTmfSchemaConfig.get("applied");

	/**
	 * Constructor
	 * @param apiClientTMF678
	 */
	public AppliedCustomerBillRateApis(ApiClient apiClientTMF678){
		logger.info("Init AppliedCustomerBillRateApis - apiClientTMF678 basePath: {}", apiClientTMF678.getBasePath());
		appliedCustomerBillingRateApi = new AppliedCustomerBillingRateApi(apiClientTMF678);	
	}

	
	/**
	 * This method retrieves a specific AppliedCustomerBillingRate by id
	 *  
	 * @param id - Identifier of the AppliedCustomerBillingRate (required)
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'product,periodCoverage')<br> 
	 * - use fields == null to get all attributes
	 * @return the {@link AppliedCustomerBillingRate} with the given id, or {@code null} if no AppliedCustomerBillingRate is found
	 * @throws ApiException if the API call fails or the resource cannot be retrieved
	 */
	
	/**
	 * Retrieves a specific {@link AppliedCustomerBillingRate} by its unique identifier.
	 *
	 * @param id      the identifier of the {@code AppliedCustomerBillingRate} to retrieve (required)
	 * @param fields  a comma-separated list of properties to include in the response (optional) <br>
	 *                - use this parameter to request specific attributes (e.g., {@code "product,periodCoverage"}) <br>
	 *                - use {@code null} or an empty string to retrieve all available attributes
	 * @return the {@link AppliedCustomerBillingRate} matching the given {@code id}
	 * @throws ApiException if the API call fails or the resource cannot be retrieved
	 */
	public AppliedCustomerBillingRate getAppliedCustomerBillingRate(String id, String fields) throws ApiException {
		logger.info("Request: getAppliedCustomerBillingRate by id {}", id);
				
		if (fields != null) {
			logger.debug("Selected attributes: [{}]", fields);
		}
		
		return appliedCustomerBillingRateApi.retrieveAppliedCustomerBillingRate(id, fields);		
	}
	
		
	/**
	 * Retrieves a list of {@link AppliedCustomerBillingRate} resources.
	 * <p>
	 * This method queries the AppliedCustomerBillingRate Management API and returns a paginated subset of results 
	 * based on the provided {@code offset}, {@code limit}, and optional filter criteria.
	 * </p>
	 *
	 * @param fields a comma-separated list of properties to include in the response (optional)<br>
	 *               - use this string to select specific fields (e.g. {@code "product,periodCoverage"})<br>
	 *               - use {@code null} to retrieve all attributes
	 * @param offset the index of the first item to return 
	 * @param limit  the maximum number of items to return 
	 * @param filter a {@link Map} of query parameters used for filtering results (optional)
	 * @return a {@link List} containing the retrieved {@link AppliedCustomerBillingRate} resources
	 * @throws ApiException if the API call fails or the resources cannot be retrieved
	 */
	public List<AppliedCustomerBillingRate> listAppliedCustomerBillingRates(String fields, int offset, int limit, Map<String, String> filter) throws ApiException {
		logger.info("Request: listAppliedCustomerBillingRates: offset={}, limit={}", offset, limit);
			
		if (filter != null && !filter.isEmpty()) {
			logger.debug("Params used in the query-string filter: {}", filter);
		}
		if (fields != null) {
			logger.debug("Selected attributes: [{}]", fields);
		}
		
		return appliedCustomerBillingRateApi.listAppliedCustomerBillingRate(fields, offset, limit, filter);
	}

	
	/**
	 * Updates an existing {@link AppliedCustomerBillingRate} resource by its unique identifier.
	 * <p>
	 * This method sends a PATCH request to the AppliedCustomerBillingRate Management API to update
	 * the specified {@link AppliedCustomerBillingRate} with the provided {@link AppliedCustomerBillingRateUpdate} data.
	 * </p>
	 *
	 * @param id the unique identifier of the {@link AppliedCustomerBillingRate} to update (required)
	 * @param AppliedCustomerBillingRateUpdate the {@link AppliedCustomerBillingRateUpdate} object containing the updated fields (required)
	 * @throws ApiException if the API call fails or the resource cannot be updated
	 */
	public void updateAppliedCustomerBillingRate(String id, AppliedCustomerBillingRateUpdate appliedCustomerBillingRateUpdate) throws ApiException {
		logger.info("Request: updateAppliedCustomerBillingRate by id {}", id);
		
		appliedCustomerBillingRateUpdate.setLastUpdate(DateTimeUtils.getCurrentUtcTime());
		
		AppliedCustomerBillingRate billUpdate = appliedCustomerBillingRateApi.updateAppliedCustomerBillingRate(id, appliedCustomerBillingRateUpdate);
		
		boolean success = (billUpdate != null && billUpdate.getId() != null);
		if (success) {
			logger.debug("Successfully updated AppliedCustomerBillingRate with id: {}", id);
		} else {
			logger.warn("Update may have failed for AppliedCustomerBillingRate id: {}", id);
		}
	}
	

	/**
	 * Creates a new {@link AppliedCustomerBillingRate} resource.
	 * <p>
	 * This method sends a creation request to the AppliedCustomerBillingRate Management API using
	 * the provided {@link AppliedCustomerBillingRateCreate} payload.
	 * If the creation is successful, it returns the identifier of the newly created resource.
	 * </p>
	 * 
	 * @param appliedCustomerBillingRateCreate the {@link AppliedCustomerBillingRateCreate} object used to create the new AppliedCustomerBillingRate (required)
	 * @return the unique identifier ({@code id}) of the created {@link AppliedCustomerBillingRate}
	 * @throws ApiException if the API call fails or the resource cannot be retrieved  
	 */
	public String createAppliedCustomerBillingRate(AppliedCustomerBillingRateCreate appliedCustomerBillingRateCreate) throws ApiException {
		logger.info("Create: AppliedCustomerBillingRate");
			
		appliedCustomerBillingRateCreate.setLastUpdate(DateTimeUtils.getCurrentUtcTime());
				
		if (appliedCustomerBillingRateCreate.getAtSchemaLocation() == null) {
			logger.debug("Setting default schemaLocation to {}", appliedSchemaLocation);
			appliedCustomerBillingRateCreate.setAtSchemaLocation(URI.create(appliedSchemaLocation));
		}

		AppliedCustomerBillingRate applied = appliedCustomerBillingRateApi.createAppliedCustomerBillingRate(appliedCustomerBillingRateCreate);
		logger.info("AppliedCustomerBillingRate saved successfully with id: {}", applied.getId());
		return applied.getId();		
	}
}
