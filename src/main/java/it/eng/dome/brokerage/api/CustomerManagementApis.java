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
	 * This method creates a Customer
	 * 
	 * @param customerCreate - CustomerCreate object used in the creation request of the Customer (required) 
	 * @return the id of the created Customer, or {@code null} if the creation failed
	 */
	public String createCustomer(CustomerCreate customerCreate) {	
		logger.info("Create: Customer");
		
		try {
			Customer customer = customerApi.createCustomer(customerCreate);
			logger.info("Customer saved successfully with id: {}", customer.getId());
			return customer.getId();
		} catch (ApiException e) {
			logger.info("Customer not saved: {}", customerCreate.toString());
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	
	/**
	 * This method updates the Customer by id
	 * 
	 * @param id - Identifier of the Customer (required) 
	 * @param customerUpdate - CustomerUpdate object used to update the Customer (required) 
	 * @return {@code true} if the update was successful,
	 *         {@code false} otherwise
	 */
	public boolean updateCustomer(String id, CustomerUpdate customerUpdate) {
		logger.info("Request: updateCustomer by id {}", id);
		
		try {
			Customer customer = customerApi.patchCustomer(id, customerUpdate);
			logger.info("Update successfully Customer with id: {}", customer.getId());
			return true;
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return false;
		}
	}
	

	/**
	 * This method retrieves a specific Customer by id
	 * 
	 * @param id - Identifier of the Customer (required)
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'status,name')<br> 
	 * - use fields == null to get all attributes
	 * @return the {@link Customer} with the given id,
	 *         or {@code null} if no Customer is found
	 */
	public Customer getCustomer(String id, String fields) {
		logger.info("Request: getCustomer by id {}", id);
		
		try {
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}
			
			return customerApi.retrieveCustomer(id, fields);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	
	/**
	 * This method retrieves a paginated list of Customer
	 * 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'name,status')<br>
	 * - use fields == null to get all attributes		
     * @param offset - the index of the first item to return (used for pagination)
     * @param limit - the maximum number of items to return
	 * @param filter - HashMap<K,V> to set query string params (optional)<br>  
	 * @return a {@link List} containing a subset of Customer
	 */
	public List<Customer> listCustomers(String fields, int offset, int limit, Map<String, String> filter) {
		logger.info("Request: listCustomers");
		
		try {			
			if (filter != null && !filter.isEmpty()) {
				logger.debug("Params used in the query-string filter: {}", filter);
			}
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}
			
			return customerApi.listCustomer(fields, offset, limit, filter);
			
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}   
	}
		
}
