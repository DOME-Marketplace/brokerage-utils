package it.eng.dome.brokerage.api;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.dome.tmforum.tmf651.v4.ApiClient;
import it.eng.dome.tmforum.tmf651.v4.ApiException;
import it.eng.dome.tmforum.tmf651.v4.api.AgreementApi;
import it.eng.dome.tmforum.tmf651.v4.model.Agreement;
import it.eng.dome.tmforum.tmf651.v4.model.AgreementCreate;
import it.eng.dome.tmforum.tmf651.v4.model.AgreementUpdate;


public class AgreementManagementApis {
	private final Logger logger = LoggerFactory.getLogger(AgreementManagementApis.class);
	private AgreementApi agreementApi;

	/**
	 * Constructor
	 * 
	 * @param apiClientTMF651
	 */
	public AgreementManagementApis(ApiClient apiClientTMF651) {
		logger.info("Init AgreementManagementApis - apiClientTMF651 basePath: {}", apiClientTMF651.getBasePath());
		agreementApi = new AgreementApi(apiClientTMF651);
	}

	
	/**
	 * Retrieves a specific {@link Agreement} by its unique identifier.
	 *
	 * @param id      the identifier of the {@code Agreement} to retrieve (required)
	 * @param fields  a comma-separated list of properties to include in the response (optional) <br>
	 *                - use this parameter to request specific attributes (e.g., {@code "status,name"}) <br>
	 *                - use {@code null} or an empty string to retrieve all available attributes
	 * @return the {@link Agreement} matching the given {@code id}
	 * @throws ApiException if the API call fails or the resource cannot be retrieved
	 */
	public Agreement getAgreement(String id, String fields) throws ApiException {
		logger.info("Request: getAgreement by id {}", id);

		if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}
			
		return agreementApi.retrieveAgreement(id, fields);
	}
	
	
	/**
	 * Retrieves a list of {@link Agreement} resources.
	 * <p>
	 * This method queries the Agreement Management API and returns a paginated subset of results 
	 * based on the provided {@code offset}, {@code limit}, and optional filter criteria.
	 * </p>
	 *
	 * @param fields a comma-separated list of properties to include in the response (optional)<br>
	 *               - use this string to select specific fields (e.g. {@code "name,description"})<br>
	 *               - use {@code null} to retrieve all attributes
	 * @param offset the index of the first item to return
	 * @param limit  the maximum number of items to return
	 * @param filter a {@link Map} of query parameters used for filtering results (optional)
	 * @return a {@link List} containing the retrieved {@link Agreement} resources
	 * @throws ApiException if the API call fails or the resources cannot be retrieved
	 */
	public List<Agreement> listAgreements(String fields, int offset, int limit, Map<String, String> filter) throws ApiException {
		logger.info("Request: listAgreements: offset={}, limit={}", offset, limit);

		if (filter != null && !filter.isEmpty()) {
			logger.debug("Params used in the query-string filter: {}", filter);
		}
		if (fields != null) {
			logger.debug("Selected attributes: [{}]", fields);
		}
		
		return agreementApi.listAgreement(fields, offset, limit, filter);   
	}
	

	/**
	 * Creates a new {@link Agreement} resource.
	 * <p>
	 * This method sends a creation request to the Agreement Management API using
	 * the provided {@link AgreementCreate} payload.
	 * If the creation is successful, it returns the identifier of the newly created resource.
	 * </p>
	 * 
	 * @param agreementCreate the {@link AgreementCreate} object used to create the new Agreement (required)
	 * @return the unique identifier ({@code id}) of the created {@link Agreement}
	 * @throws ApiException if the API call fails or the resource cannot be retrieved  
	 */
	public String createAgreement(AgreementCreate agreementCreate) throws ApiException {		
		logger.info("Create: Agreement");
		
		Agreement agreement = agreementApi.createAgreement(agreementCreate);
		logger.info("Agreement saved successfully with id: {}", agreement.getId());
		
		return agreement.getId();
	}
	
	
	/**
	 * Updates an existing {@link Agreement} resource by its unique identifier.
	 * <p>
	 * This method sends a PATCH request to the Agreement Management API to update
	 * the specified {@link Agreement} with the provided {@link AgreementUpdate} data.
	 * </p>
	 *
	 * @param id the unique identifier of the {@link Agreement} to update (required)
	 * @param AgreementUpdate the {@link AgreementUpdate} object containing the updated fields (required)
	 * @throws ApiException if the API call fails or the resource cannot be updated
	 */
	public void updateAgreement(String id, AgreementUpdate agreementUpdate) throws ApiException {
		logger.info("Request: updateAgreement by id {}", id);
		
		Agreement agreement = agreementApi.patchAgreement(id, agreementUpdate);

		boolean success = (agreement != null && agreement.getId() != null);
		if (success) {
			logger.debug("Successfully updated Agreement with id: {}", id);
		} else {
			logger.warn("Update may have failed for Agreement id: {}", id);
		}
	}
	
}
