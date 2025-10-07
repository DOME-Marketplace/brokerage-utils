package it.eng.dome.brokerage.api;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.dome.tmforum.tmf629.v4.ApiClient;
import it.eng.dome.tmforum.tmf629.v4.ApiException;
import it.eng.dome.tmforum.tmf629.v4.api.CustomerApi;
import it.eng.dome.tmforum.tmf629.v4.model.Customer;
import it.eng.dome.tmforum.tmf629.v4.model.CustomerCreate;
import it.eng.dome.tmforum.tmf629.v4.model.CustomerUpdate;


public class CustomerManagementApis {

	private final Logger logger = LoggerFactory.getLogger(CustomerManagementApis.class);
	private CustomerApi customerApi;

	/**
	 * Constructor
	 * 
	 * @param apiClientTMF629
	 */
	public CustomerManagementApis(ApiClient apiClientTMF629) {
		logger.info("Init CustomerManagementApis - apiClientTMF629 basePath: {}", apiClientTMF629.getBasePath());
		customerApi = new CustomerApi(apiClientTMF629);
	}

	
	/**
	 * Retrieves a specific {@link Customer} by its unique identifier.
	 *
	 * @param id      the identifier of the {@code Customer} to retrieve (required)
	 * @param fields  a comma-separated list of properties to include in the response (optional) <br>
	 *                - use this parameter to request specific attributes (e.g., {@code "status,name"}) <br>
	 *                - use {@code null} or an empty string to retrieve all available attributes
	 * @return the {@link Customer} matching the given {@code id}
	 * @throws ApiException if the API call fails or the resource cannot be retrieved
	 */
	public Customer getCustomer(String id, String fields) throws ApiException {
		logger.info("Request: getCustomer by id {}", id);

		if (fields != null) {
			logger.debug("Selected attributes: [{}]", fields);
		}
		
		return customerApi.retrieveCustomer(id, fields);
	}
	
	
	/**
	 * Retrieves a list of {@link Customer} resources.
	 * <p>
	 * This method queries the Customer API and returns a paginated subset of results 
	 * based on the provided {@code offset}, {@code limit}, and optional filter criteria.
	 * </p>
	 *
	 * @param fields a comma-separated list of properties to include in the response (optional)<br>
	 *               - use this string to select specific fields (e.g. {@code "name,status"})<br>
	 *               - use {@code null} to retrieve all attributes
	 * @param offset the index of the first item to return 
	 * @param limit  the maximum number of items to return 
	 * @param filter a {@link Map} of query parameters used for filtering results (optional)
	 * @return a {@link List} containing the retrieved {@link Customer} resources
	 * @throws ApiException if the API call fails or the resources cannot be retrieved
	 */
	public List<Customer> listCustomers(String fields, int offset, int limit, Map<String, String> filter) throws ApiException {
		logger.info("Request: listCustomers");
		
		if (filter != null && !filter.isEmpty()) {
			logger.debug("Params used in the query-string filter: {}", filter);
		}
		if (fields != null) {
			logger.debug("Selected attributes: [{}]", fields);
		}
		
		return customerApi.listCustomer(fields, offset, limit, filter);
	}
	
	
	/**
	 * This method creates a Customer
	 * 
	 * @param customerCreate - CustomerCreate object used in the creation request of the Customer (required) 
	 * @return the id of the created Customer, or {@code null} if the creation failed
	 * @throws @throws ApiException if the API call fails or the resource cannot be retrieved
	 */
	public String createCustomer(CustomerCreate customerCreate) throws ApiException {	
		logger.info("Create: Customer");

		Customer customer = customerApi.createCustomer(customerCreate);
		logger.info("Customer saved successfully with id: {}", customer.getId());
		return customer.getId();
	}
	
	
	/**
	 * This method updates the Customer by id
	 * 
	 * @param id - Identifier of the Customer (required) 
	 * @param customerUpdate - CustomerUpdate object used to update the Customer (required) 
	 * @throws ApiException if the API call fails or the resource cannot be retrieved 
	 */
	public void updateCustomer(String id, CustomerUpdate customerUpdate) throws ApiException {
		logger.info("Request: updateCustomer by id {}", id);
		
		Customer customer = customerApi.patchCustomer(id, customerUpdate);

		boolean success = (customer != null && customer.getId() != null);
		if (success) {
			logger.debug("Successfully updated Customer with id: {}", id);
		} else {
			logger.warn("Update may have failed for Customer id: {}", id);
		}
	}
			
}
