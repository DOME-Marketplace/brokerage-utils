package it.eng.dome.brokerage.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.eng.dome.brokerage.api.CustomerManagementApis;
import it.eng.dome.tmforum.tmf629.v4.ApiClient;
import it.eng.dome.tmforum.tmf629.v4.Configuration;
import it.eng.dome.tmforum.tmf629.v4.model.Customer;
import it.eng.dome.tmforum.tmf629.v4.model.CustomerCreate;
import it.eng.dome.tmforum.tmf629.v4.model.CustomerUpdate;

public class CustomerTest {
	final static String tmf629CustomerPath = "tmf-api/customerManagement/v4";
	final static String tmfEndpoint = "https://dome-dev.eng.it";

	public static void main(String[] args) {

		/**
		 * Create Customer
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

		/**
		 * Get All Customers
		 */
//		TestGetAllCustomers();
		
		/**
		 * Get Filtered Customers
		 */
//		TestGetFilteredCustomers();

	}

	protected static void TestGetFilteredCustomers() {

		ApiClient apiClientTmf629 = Configuration.getDefaultApiClient();
		apiClientTmf629.setBasePath(tmfEndpoint + "/" + tmf629CustomerPath);

		CustomerManagementApis apis = new CustomerManagementApis(apiClientTmf629);

		Map<String, String> filter = new HashMap<String, String>();
		filter.put("status", "Approved");
		List<Customer> customers = apis.getAllCustomers(null, filter);

		int count = 0;
		for (Customer customer : customers) {
			System.out.println(++count + " => " + customer.getId() + " " + customer.getStatus() + " " + customer.getStatusReason());
		}
	}

	protected static void TestGetAllCustomers() {

		ApiClient apiClientTmf629 = Configuration.getDefaultApiClient();
		apiClientTmf629.setBasePath(tmfEndpoint + "/" + tmf629CustomerPath);

		CustomerManagementApis apis = new CustomerManagementApis(apiClientTmf629);

		List<Customer> customers = apis.getAllCustomers(null, null);

		int count = 0;
		for (Customer customer : customers) {
			System.out.println(++count + " => " + customer.getId() + " " + customer.getStatus() + " " + customer.getStatusReason());
		}
	}
	
	protected static String TestCreateCustomer() {

		ApiClient apiClientTmf629 = Configuration.getDefaultApiClient();
		apiClientTmf629.setBasePath(tmfEndpoint + "/" + tmf629CustomerPath);

		CustomerManagementApis apis = new CustomerManagementApis(apiClientTmf629);

		CustomerCreate cc = new CustomerCreate();
		cc.setName("My customer");
		cc.setStatus("Approved");
		cc.setStatusReason("Account details checked");

		Customer c = apis.createCustomer(cc);

		return c.getId();
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
		cu.setStatusReason("Customer cannot update");
		
		return apis.updateCustomer(id, cu);
	}

}
