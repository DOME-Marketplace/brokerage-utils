package it.eng.dome.brokerage.test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import it.eng.dome.brokerage.api.CustomerBillApis;
import it.eng.dome.brokerage.api.fetch.FetchUtils;
import it.eng.dome.tmforum.tmf678.v4.ApiClient;
import it.eng.dome.tmforum.tmf678.v4.ApiException;
import it.eng.dome.tmforum.tmf678.v4.Configuration;
import it.eng.dome.tmforum.tmf678.v4.model.CustomerBillCreate;
import it.eng.dome.tmforum.tmf678.v4.model.Money;
import it.eng.dome.tmforum.tmf678.v4.model.StateValue;

public class CustomerBillApisTest {
	

	final static String tmf678CustomerBillPath = "tmf-api/customerBillManagement/v4";
	final static String tmfEndpoint = "https://dome-dev.eng.it"; //"https://tmf.dome-marketplace-sbx.org";

	@Test
	public void RunTest() {
		
//		TestCustomerBillFiltered();		
//		TestCustomerBillById();
		
//		TestCreateBillCustomer();
	}
	
	public static void TestCreateBillCustomer() {
		ApiClient apiClientTmf678 = Configuration.getDefaultApiClient();
		apiClientTmf678.setBasePath(tmfEndpoint + "/" + tmf678CustomerBillPath);
		
		CustomerBillApis apis = new CustomerBillApis(apiClientTmf678);
		
		CustomerBillCreate cbc = new CustomerBillCreate();
		cbc.setCategory("category");
		Money amountDue = new Money();
		amountDue.setUnit("EUR");
		amountDue.setValue(Float.valueOf("12.05"));
		cbc.setAmountDue(amountDue);
		cbc.setState(StateValue.ON_HOLD);
		
		String id = null;
		try {
			id = apis.createCustomerBill(cbc);
		} catch (ApiException e) {
			System.err.println("Error: " + e.getMessage());
		}
		
		System.out.println("CustomerBill id: " + id);
	}
	
	public static void TestCustomerBillFiltered() {
		ApiClient apiClientTmf678 = Configuration.getDefaultApiClient();
		apiClientTmf678.setBasePath(tmfEndpoint + "/" + tmf678CustomerBillPath);
		
		CustomerBillApis apis = new CustomerBillApis(apiClientTmf678);
		AtomicInteger count = new AtomicInteger(0);
		
		// filtered
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("state", "new");
		filter.put("amountDue.tmfValue.st", "0");
		//filter.put("amountDue.tmfValue.eq", "0");
		//filter.put("taxIncludedAmount.value.eq", "5");
		
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
		String id = "urn:ngsi-ld:customer-bill:3bff8fc2-7587-4c4e-baec-8b4a3e7ee335";
		try {
			System.out.println("detail of state: " + apis.getCustomerBill(id, null).getLastUpdate());
		} catch (ApiException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
	

}
