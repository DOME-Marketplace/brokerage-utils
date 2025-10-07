package it.eng.dome.brokerage.api;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.dome.tmforum.tmf678.v4.ApiClient;
import it.eng.dome.tmforum.tmf678.v4.ApiException;
import it.eng.dome.tmforum.tmf678.v4.api.CustomerBillApi;
import it.eng.dome.tmforum.tmf678.v4.api.CustomerBillExtensionApi;
import it.eng.dome.tmforum.tmf678.v4.model.CustomerBill;
import it.eng.dome.tmforum.tmf678.v4.model.CustomerBillCreate;

public class CustomerBillApis {
	
	private final Logger logger = LoggerFactory.getLogger(CustomerBillApis.class);
	private CustomerBillApi customerBill;
	private CustomerBillExtensionApi customerBillExtension;

	/**
	 * Constructor
	 * @param apiClientTMF678
	 */
	public CustomerBillApis(ApiClient apiClientTMF678){
		logger.info("Init CustomerBillApis - apiClientTMF678 basePath: {}", apiClientTMF678.getBasePath());
		customerBill = new CustomerBillApi(apiClientTMF678);
		customerBillExtension = new CustomerBillExtensionApi(apiClientTMF678);
	}
	
	
	/**
	 * Retrieves a specific {@link CustomerBill} by its unique identifier.
	 *
	 * @param id      the identifier of the {@code CustomerBill} to retrieve (required)
	 * @param fields  a comma-separated list of properties to include in the response (optional) <br>
	 *                - use this parameter to request specific attributes (e.g., {@code "state,paymentDueDate"}) <br>
	 *                - use {@code null} or an empty string to retrieve all available attributes
	 * @return the {@link CustomerBill} matching the given {@code id}
	 * @throws ApiException if the API call fails or the resource cannot be retrieved
	 */
	public CustomerBill getCustomerBill(String id, String fields) throws ApiException {
		logger.info("Request: getCustomerBill by id {}", id);
		
		if (fields != null) {
			logger.debug("Selected attributes: [{}]", fields);
		}
		
		return customerBill.retrieveCustomerBill(id, fields);
	}
	
	
	/**
	 * Retrieves a list of {@link CustomerBill} resources.
	 * <p>
	 * This method queries the CustomerBill API and returns a paginated subset of results 
	 * based on the provided {@code offset}, {@code limit}, and optional filter criteria.
	 * </p>
	 *
	 * @param fields a comma-separated list of properties to include in the response (optional)<br>
	 *               - use this string to select specific fields (e.g. {@code "state,paymentDueDate"})<br>
	 *               - use {@code null} to retrieve all attributes
	 * @param offset the index of the first item to return 
	 * @param limit  the maximum number of items to return 
	 * @param filter a {@link Map} of query parameters used for filtering results (optional)
	 * @return a {@link List} containing the retrieved {@link CustomerBill} resources
	 * @throws ApiException if the API call fails or the resources cannot be retrieved
	 */
	public List<CustomerBill> listCustomerBills(String fields, int offset, int limit, Map<String, String> filter) throws ApiException {
		logger.info("Request: listCustomerBills");
		
		if (filter != null && !filter.isEmpty()) {
			logger.debug("Params used in the query-string filter: {}", filter);
		}
		if (fields != null) {
			logger.debug("Selected attributes: [{}]", fields);
		}
		
		return customerBill.listCustomerBill(fields, offset, limit, filter);
	}
	
	
	/**
	 * This method creates a CustomerBill
	 * 
	 * @param customerBillCreate - CustomerBillCreate object used in the creation request of the CustomerBill (required) 
	 * @return the id of the created CustomerBill, or {@code null} if the creation failed
	 * @throws ApiException if the API call fails or the resource cannot be retrieved
	 */
	public String createCustomerBill(CustomerBillCreate customerBillCreate) throws ApiException {
		logger.info("Request: CustomerBill");

		CustomerBill customerBill = customerBillExtension.createCustomerBill(customerBillCreate);
		logger.info("CustomerBill saved successfully with id: {}", customerBill.getId());
		return customerBill.getId();
	}

}
