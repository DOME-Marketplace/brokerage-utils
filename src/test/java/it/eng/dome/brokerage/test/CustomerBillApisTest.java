package it.eng.dome.brokerage.test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import it.eng.dome.brokerage.api.CustomerBillApis;
import it.eng.dome.brokerage.api.fetch.FetchUtils;
import it.eng.dome.tmforum.tmf678.v4.ApiClient;
import it.eng.dome.tmforum.tmf678.v4.ApiException;
import it.eng.dome.tmforum.tmf678.v4.Configuration;

public class CustomerBillApisTest {
	

	final static String tmf678CustomerBillPath = "tmf-api/customerBillManagement/v4";
	final static String tmfEndpoint = "https://tmf.dome-marketplace-sbx.org";

	public static void main(String[] args) {
		
		TestCustomerBillFiltered();
		
		TestCustomerBillById();
	}
	
	
	public static void TestCustomerBillFiltered() {
		ApiClient apiClientTmf678 = Configuration.getDefaultApiClient();
		apiClientTmf678.setBasePath(tmfEndpoint + "/" + tmf678CustomerBillPath);
		
		CustomerBillApis apis = new CustomerBillApis(apiClientTmf678);
		AtomicInteger count = new AtomicInteger(0);
		
		// filtered
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("state", "new");
		filter.put("amountDue.tmfValue.gt", "0");
		
		FetchUtils.streamAll(
	        apis::listCustomerBills, 	// method reference
	        null,                       // fields
	        filter, 				    // filter
	        100                         // pageSize
		) 
		.forEach(cb -> { 
			count.incrementAndGet();
			System.out.println(count + " " + cb.getId() + " → " + cb.getAmountDue().getValue() + " / " + cb.getState());
			}
		);
		
		System.out.println("CustomerBill found: " + count);
	}
	
	
	public static void TestCustomerBillById() {
		ApiClient apiClientTmf678 = Configuration.getDefaultApiClient();
		apiClientTmf678.setBasePath(tmfEndpoint + "/" + tmf678CustomerBillPath);
		
		CustomerBillApis apis = new CustomerBillApis(apiClientTmf678);
			
		// get by ID
		String id = "urn:ngsi-ld:customer-bill:3adf91fe-4896-4820-8ae8-db29864e03a3";
		try {
			System.out.println("detail of state: " + apis.getCustomerBill(id, null).getState().getValue());
		} catch (ApiException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
	

}
