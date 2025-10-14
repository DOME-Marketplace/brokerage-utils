package it.eng.dome.brokerage.api;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.dome.tmforum.tmf666.v4.ApiClient;
import it.eng.dome.tmforum.tmf666.v4.ApiException;
import it.eng.dome.tmforum.tmf666.v4.api.BillFormatApi;
import it.eng.dome.tmforum.tmf666.v4.api.BillingAccountApi;
import it.eng.dome.tmforum.tmf666.v4.api.PartyAccountApi;
import it.eng.dome.tmforum.tmf666.v4.model.BillFormat;
import it.eng.dome.tmforum.tmf666.v4.model.BillFormatCreate;
import it.eng.dome.tmforum.tmf666.v4.model.BillFormatUpdate;
import it.eng.dome.tmforum.tmf666.v4.model.BillingAccount;
import it.eng.dome.tmforum.tmf666.v4.model.PartyAccount;


public class AccountManagementApis {
	
	private final Logger logger = LoggerFactory.getLogger(AccountManagementApis.class);
	private PartyAccountApi partyAccountApi;
	private BillingAccountApi billingAccountApi;
	private BillFormatApi billFormatApi;

	/**
	 * Constructor
	 * 
	 * @param apiClientTMF666
	 */
	public AccountManagementApis(ApiClient apiClientTMF666) {
		logger.info("Init AccountManagementApis - apiClientTMF666 basePath: {}", apiClientTMF666.getBasePath());
		partyAccountApi = new PartyAccountApi(apiClientTMF666);
		billingAccountApi = new BillingAccountApi(apiClientTMF666);
		billFormatApi = new BillFormatApi(apiClientTMF666);
	}
	
	
	/**
	 * Retrieves a specific {@link PartyAccount} by its unique identifier.
	 *
	 * @param id      the identifier of the {@code PartyAccount} to retrieve (required)
	 * @param fields  a comma-separated list of properties to include in the response (optional) <br>
	 *                - use this parameter to request specific attributes (e.g., {@code "status,name"}) <br>
	 *                - use {@code null} or an empty string to retrieve all available attributes
	 * @return the {@link PartyAccount} matching the given {@code id}
	 * @throws ApiException if the API call fails or the resource cannot be retrieved
	 */
	public PartyAccount getPartyAccount(String id, String fields) throws ApiException  {
		logger.info("Request: getPartyAccount by id {}", id);

		if (fields != null) {
			logger.debug("Selected attributes: [{}]", fields);
		}
		
		return partyAccountApi.retrievePartyAccount(id, fields);
	}
	
		
	/**
	 * Retrieves a list of {@link PartyAccount} resources.
	 * <p>
	 * This method queries the Account Management API and returns a paginated subset of results 
	 * based on the provided {@code offset}, {@code limit}, and optional filter criteria.
	 * </p>
	 *
	 * @param fields a comma-separated list of properties to include in the response (optional)<br>
	 *               - use this string to select specific fields (e.g. {@code "name,description"})<br>
	 *               - use {@code null} to retrieve all attributes
	 * @param offset the index of the first item to return
	 * @param limit  the maximum number of items to return
	 * @param filter a {@link Map} of query parameters used for filtering results (optional)
	 * @return a {@link List} containing the retrieved {@link PartyAccount} resources
	 * @throws ApiException if the API call fails or the resources cannot be retrieved
	 */
	public List<PartyAccount> listPartyAccounts(String fields, int offset, int limit, Map<String, String> filter) throws ApiException {
		logger.info("Request: listPartyAccounts");
				
		if (filter != null && !filter.isEmpty()) {
			logger.debug("Params used in the query-string filter: {}", filter);
		}
		if (fields != null) {
			logger.debug("Selected attributes: [{}]", fields);
		}
		
		return partyAccountApi.listPartyAccount(fields, offset, limit, filter);   
	}
	
		
	/**
	 * Retrieves a specific {@link BillingAccount} by its unique identifier.
	 * 
	 * @param id     the unique identifier of the {@link BillingAccount} (required)
	 * @param fields a comma-separated list of properties to include in the response (optional)<br>
	 *               - use this string to select specific fields (e.g. {@code "status,name"})<br>
	 *               - use {@code null} to retrieve all attributes
	 * @return the {@link BillingAccount} matching the given {@code id}
	 * @throws ApiException if the API call fails or the resource cannot be retrieved 
	 */
	public BillingAccount getBillingAccount(String id, String fields) throws ApiException {
		logger.info("Request: getBillingAccount by id {}", id);

		if (fields != null) {
			logger.debug("Selected attributes: [{}]", fields);
		}
		
		return billingAccountApi.retrieveBillingAccount(id, fields);
	}
	
	
	/**
	 * Retrieves a list of {@link BillingAccount} resources.
	 * <p>
	 * This method queries the Account Management API and returns a paginated subset of results 
	 * based on the provided {@code offset}, {@code limit}, and optional filter criteria.
	 * </p>
	 *
	 * @param fields a comma-separated list of properties to include in the response (optional)<br>
	 *               - use this string to select specific fields (e.g. {@code "name,description"})<br>
	 *               - use {@code null} to retrieve all attributes
	 * @param offset the index of the first item to return
	 * @param limit  the maximum number of items to return
	 * @param filter a {@link Map} of query parameters used for filtering results (optional)
	 * @return a {@link List} containing the retrieved {@link BillingAccount} resources
	 * @throws ApiException if the API call fails or the resources cannot be retrieved
	 */
	public List<BillingAccount> listBillingAccounts(String fields, int offset, int limit, Map<String, String> filter) throws ApiException {
		logger.info("Request: listBillingAccounts");
			
		if (filter != null && !filter.isEmpty()) {
			logger.debug("Params used in the query-string filter: {}", filter);
		}
		if (fields != null) {
			logger.debug("Selected attributes: [{}]", fields);
		}
		
		return billingAccountApi.listBillingAccount(fields, offset, limit, filter);
	}
	
		
	/**
	 * Creates a new {@link BillFormat} resource.
	 * <p>
	 * This method sends a creation request to the Account Management API using
	 * the provided {@link BillFormatCreate} payload.
	 * If the creation is successful, it returns the identifier of the newly created resource.
	 * </p>
	 * 
	 * @param billFormatCreate the {@link BillFormatCreate} object used to create the new BillFormat (required)
	 * @return the unique identifier ({@code id}) of the created {@link BillFormat}
	 * @throws ApiException if the API call fails or the resource cannot be retrieved  
	 */
	public String createBillFormat(BillFormatCreate billFormatCreate) throws ApiException {	
		logger.info("Create: BillFormat");

		BillFormat billFormat = billFormatApi.createBillFormat(billFormatCreate);
		logger.info("BillFormat saved successfully with id: {}", billFormat.getId());
		
		return billFormat.getId();
	}
	
	
	/**
	 * Updates an existing {@link BillFormat} resource by its unique identifier.
	 * <p>
	 * This method sends a PATCH request to the Account Management API to update
	 * the specified {@link BillFormat} with the provided {@link BillFormatUpdate} data.
	 * </p>
	 *
	 * @param id the unique identifier of the {@link BillFormat} to update (required)
	 * @param billFormatUpdate the {@link BillFormatUpdate} object containing the updated fields (required)
	 * @throws ApiException if the API call fails or the resource cannot be updated
	 */
	public void updateBillFormat(String id, BillFormatUpdate billFormatUpdate) throws ApiException {
		logger.info("Request: updateBillFormat by id {}", id);

		BillFormat billFormat = billFormatApi.patchBillFormat(id, billFormatUpdate);
		
		boolean success = (billFormat != null && billFormat.getId() != null);
		if (success) {
			logger.debug("Successfully updated BillFormat with id: {}", id);
		} else {
			logger.warn("Update may have failed for BillFormat id: {}", id);
		}
	}
	
		
	/**
	 * Retrieves a specific {@link BillFormat} by its unique identifier.
	 *
	 * @param id      the identifier of the {@code BillFormat} to retrieve (required)
	 * @param fields  a comma-separated list of properties to include in the response (optional) <br>
	 *                - use this parameter to request specific attributes (e.g., {@code "status,name"}) <br>
	 *                - use {@code null} or an empty string to retrieve all available attributes
	 * @return the {@link BillFormat} matching the given {@code id}
	 * @throws ApiException if the API call fails or the resource cannot be retrieved
	 */
	public BillFormat getBillFormat(String id, String fields) throws ApiException {
		logger.info("Request: getBillFormat by id {}", id);

		if (fields != null) {
			logger.debug("Selected attributes: [{}]", fields);
		}
		
		return billFormatApi.retrieveBillFormat(id, fields);
	}
	
	
	/**
	 * Retrieves a list of {@link BillFormat} resources.
	 * <p>
	 * This method queries the BillFormat API and returns a paginated subset of results 
	 * based on the provided {@code offset}, {@code limit}, and optional filter criteria.
	 * </p>
	 *
	 * @param fields a comma-separated list of properties to include in the response (optional)<br>
	 *               - use this string to select specific fields (e.g. {@code "name,description"})<br>
	 *               - use {@code null} to retrieve all attributes
	 * @param offset the index of the first item to return
	 * @param limit  the maximum number of items to return
	 * @param filter a {@link Map} of query parameters used for filtering results (optional)
	 * @return a {@link List} containing the retrieved {@link BillFormat} resources
	 * @throws ApiException if the API call fails or the resources cannot be retrieved
	 */
	public List<BillFormat> listBillFormats(String fields, int offset, int limit, Map<String, String> filter) throws ApiException {
		logger.info("Request: listBillFormats");
		
		if (filter != null && !filter.isEmpty()) {
			logger.debug("Params used in the query-string filter: {}", filter);
		}
		if (fields != null) {
			logger.debug("Selected attributes: [{}]", fields);
		}
		
		return billFormatApi.listBillFormat(fields, offset, limit, filter);
	}

}
