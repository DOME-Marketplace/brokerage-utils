package it.eng.dome.brokerage.test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import it.eng.dome.brokerage.api.CustomerManagementApis;
import it.eng.dome.brokerage.api.fetch.FetchUtils;
import it.eng.dome.tmforum.tmf629.v4.ApiClient;
import it.eng.dome.tmforum.tmf629.v4.Configuration;
import it.eng.dome.tmforum.tmf629.v4.model.Customer;
import it.eng.dome.tmforum.tmf629.v4.model.CustomerCreate;
import it.eng.dome.tmforum.tmf629.v4.model.CustomerUpdate;

public class CustomerManagementApisTest {
	final static String tmf629CustomerPath = "tmf-api/customerManagement/v4";
	final static String tmfEndpoint = "https://dome-dev.eng.it";

	public static void main(String[] args) {

		/**
		 * Get All Customers
		 */
		TestGetAllCustomers();

		/**
		 * Get Filtered Customers
		 */
//		TestGetFilteredCustomers();
		
		
		/**
		 * Create Customer and Get by ID
		 */
//		String id = TestCreateCustomer();
//		if (id != null) {
//			Customer c = TestGetCustomer(id);
//			System.out.println(c.getId() + " " + c.getStatus() + " " + c.getStatusReason());
//		}
		
		/**
		 * Update Customer
		 */
//		String id = "urn:ngsi-ld:customer:5c49b0ec-5c5b-4c88-a85a-7e408624fec8";
//		TestUpdateCustomer(id);

	}
	
	protected static void TestGetAllCustomers() {

		ApiClient apiClientTmf629 = Configuration.getDefaultApiClient();
		apiClientTmf629.setBasePath(tmfEndpoint + "/" + tmf629CustomerPath);

		CustomerManagementApis apis = new CustomerManagementApis(apiClientTmf629);
		AtomicInteger count = new AtomicInteger(0);
		
		FetchUtils.streamAll(
	        apis::listCustomers, 	// method reference
	        null,                       // fields
	        null, 				    	// filter
	        100                         // pageSize
		) 
		.forEach(customer -> { 
			count.incrementAndGet();
			System.out.println(count + " " + customer.getId() + " → " + customer.getName() + " / " + customer.getStatus());
			}
		);		
		
		System.out.println("Customer found: " + count);
	}
	
	
	protected static void TestGetFilteredCustomers() {

		ApiClient apiClientTmf629 = Configuration.getDefaultApiClient();
		apiClientTmf629.setBasePath(tmfEndpoint + "/" + tmf629CustomerPath);

		CustomerManagementApis apis = new CustomerManagementApis(apiClientTmf629);
		AtomicInteger count = new AtomicInteger(0);
		
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("status", "Approved");
		String fields = "name,status";
		
		FetchUtils.streamAll(
	        apis::listCustomers, 		// method reference
	        fields,                     // fields
	        filter, 				    // filter
	        100                         // pageSize
		) 
		.forEach(customer -> { 
			count.incrementAndGet();
			System.out.println(count + " " + customer.getId() + " → " + customer.getName() + " / " + customer.getStatus());
			}
		);		
		
		System.out.println("Customer found: " + count);
	}
	
	
	protected static String TestCreateCustomer() {

		ApiClient apiClientTmf629 = Configuration.getDefaultApiClient();
		apiClientTmf629.setBasePath(tmfEndpoint + "/" + tmf629CustomerPath);

		CustomerManagementApis apis = new CustomerManagementApis(apiClientTmf629);

		CustomerCreate cc = new CustomerCreate();
		cc.setName("Just a simple test");
		cc.setStatus("Approved");
		cc.setStatusReason("Account details checked");

		String id = apis.createCustomer(cc);

		return id;
	}
		
	protected static Customer TestGetCustomer(String id) {

		ApiClient apiClientTmf629 = Configuration.getDefaultApiClient();
		apiClientTmf629.setBasePath(tmfEndpoint + "/" + tmf629CustomerPath);

		CustomerManagementApis apis = new CustomerManagementApis(apiClientTmf629);
		return apis.getCustomer(id, null);
	}
	
	
	protected static boolean TestUpdateCustomer(String id) {

		ApiClient apiClientTmf629 = Configuration.getDefaultApiClient();
		apiClientTmf629.setBasePath(tmfEndpoint + "/" + tmf629CustomerPath);

		CustomerManagementApis apis = new CustomerManagementApis(apiClientTmf629);
		
		CustomerUpdate cu = new CustomerUpdate();
		cu.setStatus("Rejected");
		cu.setStatusReason("The customer cannot be updated");
		
		return apis.updateCustomer(id, cu);
	}

}
