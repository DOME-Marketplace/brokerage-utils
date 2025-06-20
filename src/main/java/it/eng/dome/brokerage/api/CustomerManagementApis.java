package it.eng.dome.brokerage.api;

import java.util.ArrayList;
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
	private final int LIMIT = 100;

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
	 * @param CustomerCreate - CustomerCreate object used in the creation request of the Customer (required) 
	 * @return Customer
	 */
	public Customer createCustomer(CustomerCreate customerCreate) {		
		try {
			return customerApi.createCustomer(customerCreate);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	
	/**
	 * This method updates the Customer by Id
	 * 
	 * @param customerId - Identifier of the Customer (required) 
	 * @param customerUpdate - CustomerUpdate object used to update the Customer (required) 
	 * @return boolean
	 */
	public boolean updateCustomer(String customerId, CustomerUpdate customerUpdate) {
		logger.info("Request: updateCustomer");
		try {
			Customer customer = customerApi.patchCustomer(customerId, customerUpdate);
			logger.info("Update Customer with id: {}", customer.getId());
			return true;
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return false;
		}
	}

	/**
	 * This method retrieves a specific Customer by ID
	 * 
	 * @param customerId - Identifier of the Customer (required)
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'status,name')<br> 
	 * - use fields == null to get all attributes
	 * @return Customer
	 */
	public Customer getCustomer(String customerId, String fields) {
		try {
			return customerApi.retrieveCustomer(customerId, fields);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	/**
	 * This method retrieves the list of UsCustomerage
	 * 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'status,name')<br> 
	 * - use fields == null to get all attributes
	 * @param filter - HashMap<K,V> to set query string params (optional)<br> 
	 * @return List&lt;Customer&gt;
	 */
	public List<Customer> getAllCustomers(String fields, Map<String, String> filter) {
		logger.info("Request: getAllCustomers");
		List<Customer> all = new ArrayList<Customer>();
		
		if (filter != null && !filter.isEmpty()) {
			logger.debug("Params used in the query-string filter: {}", filter);
		}
		
		getAllCustomers(all, fields, 0, filter);
		logger.info("Number of Customers: {}", all.size());
		return all;
	}
		
	private void getAllCustomers(List<Customer> list, String fields, int start, Map<String, String> filter) {
		int offset = start * LIMIT;

		try {
			List<Customer> customerList =  customerApi.listCustomer(fields, offset, LIMIT, filter);

			if (!customerList.isEmpty()) {
				list.addAll(customerList);
				getAllCustomers(list, fields, start + 1, filter);				
			}else {
				return;
			}
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return;
		}		
	}
	
}
