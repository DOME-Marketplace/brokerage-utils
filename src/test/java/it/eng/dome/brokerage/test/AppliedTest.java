package it.eng.dome.brokerage.test;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.eng.dome.brokerage.api.AppliedCustomerBillRateApis;
import it.eng.dome.tmforum.tmf678.v4.ApiClient;
import it.eng.dome.tmforum.tmf678.v4.ApiException;
import it.eng.dome.tmforum.tmf678.v4.Configuration;
import it.eng.dome.tmforum.tmf678.v4.api.AppliedCustomerBillingRateApi;
import it.eng.dome.tmforum.tmf678.v4.model.AppliedCustomerBillingRate;
import it.eng.dome.tmforum.tmf678.v4.model.TimePeriod;

public class AppliedTest {

	final static String tmf678CustomerBillPath = "tmf-api/customerBillManagement/v4";
	final static String tmfEndpoint = "https://dome-dev.eng.it";

	public static void main(String[] args) {
		
		//TestApiClient();
		//TestApis();
		//TestFilter();
		
		TestIsAlreadyBilled();
	}
	
	
	public static void TestIsAlreadyBilled() {
		ApiClient apiClientTmf678 = Configuration.getDefaultApiClient();
		apiClientTmf678.setBasePath(tmfEndpoint + "/" + tmf678CustomerBillPath);
		
		AppliedCustomerBillRateApis apis = new AppliedCustomerBillRateApis(apiClientTmf678);

		Map<String, String> filter = new HashMap<String, String>();
		
		TimePeriod tp = new TimePeriod();
		tp.setEndDateTime(OffsetDateTime.parse("2025-07-01T10:04:38.983Z"));
		tp.setStartDateTime(OffsetDateTime.parse("2025-06-30T10:04:38.983Z"));
		
		filter.put("rateType", "pay-per-use");
		filter.put("periodCoverage.endDateTime.eq", tp.getEndDateTime().toString());
		filter.put("periodCoverage.startDateTime.eq", tp.getStartDateTime().toString());
		filter.put("product.id", "urn:ngsi-ld:product:f942601c-1902-4b09-a498-23d844d21972");
		
		List<AppliedCustomerBillingRate> applied = apis.getAllAppliedCustomerBillingRates("isBilled,type", filter);
		int count = 0;
	 		
		for (AppliedCustomerBillingRate apply : applied) {
			System.out.println(++count + " => " + apply.getId() + " / " + apply.getIsBilled() + " / " + apply.getType() );
		}
	}
	
	public static void TestFilter() {
		ApiClient apiClientTmf678 = Configuration.getDefaultApiClient();
		apiClientTmf678.setBasePath(tmfEndpoint + "/" + tmf678CustomerBillPath);
		
		AppliedCustomerBillRateApis apis = new AppliedCustomerBillRateApis(apiClientTmf678);

		Map<String, String> filter = new HashMap<String, String>();
		//filter.put("isBilled", "false");
		//filter.put("name", "Applied Customer Bill Rate #54");
		//filter.put("type", "applied-customer-billing-rate");
		//filter.put("rateType", "recurring");
		String mydate = "2025-04-22T15:18:52.065849400Z";
		OffsetDateTime dateRef = OffsetDateTime.parse(mydate);
		//filter.put("date.gt", mydate); // gt, lt, eq OK
		//filter.put("periodCoverage.endDateTime", mydate);
		filter.put("relatedParty.name", "OLIMPO");
		
		List<AppliedCustomerBillingRate> applied = apis.getAllAppliedCustomerBillingRates(null, filter);
		int count = 0;
	 
		
		for (AppliedCustomerBillingRate apply : applied) {
			System.out.println(++count + " => " + apply.getId() + " / " + apply.getIsBilled() + " / " + apply.getType() );
			/*
			if (dateRef.isBefore(apply.getPeriodCoverage().getEndDateTime())) {
				System.out.println(dateRef  + " is before " + apply.getPeriodCoverage().getEndDateTime());
			} else {
				System.out.println(dateRef  + " is after " + apply.getPeriodCoverage().getEndDateTime());
			}*/
			
			if (dateRef.isBefore(apply.getDate())) {
				System.out.println(dateRef  + " is before " + apply.getDate());
			} else {
				System.out.println(dateRef  + " is after " + apply.getDate());
			}
		}
	}
	
	public static void TestApis() {
		
		ApiClient apiClientTmf678 = Configuration.getDefaultApiClient();
		apiClientTmf678.setBasePath(tmfEndpoint + "/" + tmf678CustomerBillPath);
		
		AppliedCustomerBillRateApis apis = new AppliedCustomerBillRateApis(apiClientTmf678);
		AppliedCustomerBillingRate apply = apis.getAppliedCustomerBillingRate("urn:ngsi-ld:applied-customer-billing-rate:8f99c450-139f-40be-b7ec-8fbd48e357de", null);
		if (apply != null) {
			System.out.println(apply.getName());
		}
		
		List<AppliedCustomerBillingRate> applied = apis.getAllAppliedCustomerBillingRates(null, null);
		System.out.println(applied.size());
		
	}
	
	public static void TestApiClient() {
		try {
			ApiClient apiClientTmf678 = Configuration.getDefaultApiClient();
			
			apiClientTmf678.setBasePath(tmfEndpoint + "/" + tmf678CustomerBillPath);

			AppliedCustomerBillingRateApi applied = new AppliedCustomerBillingRateApi(apiClientTmf678);

			AppliedCustomerBillingRate apply = applied.retrieveAppliedCustomerBillingRate("urn:ngsi-ld:applied-customer-billing-rate:8f99c450-139f-40be-b7ec-8fbd48e357de", null);
			System.out.println(apply.getName());

		} catch (ApiException e) {
			//System.out.println(e.getMessage());
			System.out.println(e.getResponseBody());
		}
	}

}
