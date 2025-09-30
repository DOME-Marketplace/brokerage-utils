package it.eng.dome.brokerage.test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import it.eng.dome.brokerage.api.CustomerBillApis;
import it.eng.dome.brokerage.api.page.PaginationUtils;
import it.eng.dome.tmforum.tmf678.v4.ApiClient;
import it.eng.dome.tmforum.tmf678.v4.Configuration;

public class CustomerBillApisTest {
	

	final static String tmf678CustomerBillPath = "tmf-api/customerBillManagement/v4";
	final static String tmfEndpoint = "https://dome-dev.eng.it";

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
		
		PaginationUtils.streamAll(
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
	}
	
	
	public static void TestCustomerBillById() {
		ApiClient apiClientTmf678 = Configuration.getDefaultApiClient();
		apiClientTmf678.setBasePath(tmfEndpoint + "/" + tmf678CustomerBillPath);
		
		CustomerBillApis apis = new CustomerBillApis(apiClientTmf678);
			
		// get by ID
		String id = "urn:ngsi-ld:customer-bill:f3fc3b17-7cb8-4e92-b49e-e3b4067226da";
		System.out.println("detail of state: " + apis.getCustomerBill(id, null).getState().getValue());
	}
	

}
