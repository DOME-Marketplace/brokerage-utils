package it.eng.dome.brokerage.test;

import java.util.List;

import it.eng.dome.brokerage.api.AppliedCustomerBillRateApis;
import it.eng.dome.tmforum.tmf678.v4.ApiClient;
import it.eng.dome.tmforum.tmf678.v4.ApiException;
import it.eng.dome.tmforum.tmf678.v4.Configuration;
import it.eng.dome.tmforum.tmf678.v4.api.AppliedCustomerBillingRateApi;
import it.eng.dome.tmforum.tmf678.v4.model.AppliedCustomerBillingRate;

public class AppliedTest {

	final static String tmf678CustomerBillPath = "tmf-api/customerBillManagement/v4";
	final static String tmfEndpoint = "https://<tmforum-endpoint>";

	public static void main(String[] args) {
		TestApiClient();
		TestApis();
	}
	
	private static void TestApis() {
		
		ApiClient apiClientTmf678 = Configuration.getDefaultApiClient();
		apiClientTmf678.setBasePath(tmfEndpoint + "/" + tmf678CustomerBillPath);
		
		AppliedCustomerBillRateApis apis = new AppliedCustomerBillRateApis(apiClientTmf678);
		AppliedCustomerBillingRate apply = apis.getAppliedCustomerBillingRate("urn:ngsi-ld:applied-customer-billing-rate:ddda692f-8fec-4c12-9e21-1b03f05cd5881", null);
		if (apply != null) {
			System.out.println(apply.getName());
		}
		
		List<AppliedCustomerBillingRate> applied = apis.getAllAppliedCustomerBillingRates(null);
		System.out.println(applied.size());
		
	}
	
	private static void TestApiClient() {
		try {
			ApiClient apiClientTmf678 = Configuration.getDefaultApiClient();
			
			apiClientTmf678.setBasePath(tmfEndpoint + "/" + tmf678CustomerBillPath);

			AppliedCustomerBillingRateApi applied = new AppliedCustomerBillingRateApi(apiClientTmf678);

			AppliedCustomerBillingRate apply = applied.retrieveAppliedCustomerBillingRate("urn:ngsi-ld:applied-customer-billing-rate:ddda692f-8fec-4c12-9e21-1b03f05cd5881", null);
			System.out.println(apply.getName());

		} catch (ApiException e) {
			//System.out.println(e.getMessage());
			System.out.println(e.getResponseBody());
		}
	}

}
