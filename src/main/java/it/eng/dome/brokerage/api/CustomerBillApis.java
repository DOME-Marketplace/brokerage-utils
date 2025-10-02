package it.eng.dome.brokerage.api;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.dome.brokerage.api.page.Page;
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
	 * This method retrieves a specific CustomerBill by id
	 *  
	 * @param id - Identifier of the CustomerBill (required)
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'state,paymentDueDate')<br> 
	 * - use fields == null to get all attributes
	 * @return the {@link CustomerBill} with the given id,
	 *         or {@code null} if no CustomerBill is found
	 */
	public CustomerBill getCustomerBill(String id, String fields) {
		logger.info("Request: getCustomerBill by id {}", id);
		
		try {
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}
			
			return customerBill.retrieveCustomerBill(id, fields);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	
	/**
	 * This method retrieves a paginated list of CustomerBill
	 * 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'state,paymentDueDate')<br> 
	 * - use fields == null to get all attributes		
     * @param offset - the index of the first item to return (used for pagination)
     * @param limit - the maximum number of items to return
	 * @param filter - HashMap<K,V> to set query string params (optional)<br>  
	 * @return a {@link Page} containing a subset of CustomerBill
	 */
	public Page<CustomerBill> listCustomerBills(String fields, int offset, int limit, Map<String, String> filter) {
		logger.info("Request: listCustomerBills");
		
		try {
			
			if (filter != null && !filter.isEmpty()) {
				logger.debug("Params used in the query-string filter: {}", filter);
			}
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}
			
			List<CustomerBill> items = customerBill.listCustomerBill(fields, offset, limit, filter);
			boolean hasNext = items.size() == limit;
			
			return new Page<>(items, offset, limit, hasNext);
			
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}   
	}
	
	
	/**
	 * This method creates a CustomerBill
	 * 
	 * @param customerBillCreate - CustomerBillCreate object used in the creation request of the CustomerBill (required) 
	 * @return the id of the created CustomerBill, or {@code null} if the creation failed
	 */
	public String createCustomerBill(CustomerBillCreate customerBillCreate) {
		logger.info("Request: CustomerBill");
		try {
			CustomerBill customerBill = customerBillExtension.createCustomerBill(customerBillCreate);
			logger.info("CustomerBill saved successfully with id: {}", customerBill.getId());
			return customerBill.getId();
		} catch (ApiException e) {
			logger.info("CustomerBill not saved: {}", customerBillCreate.toString());
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}

}
