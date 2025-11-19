package it.eng.dome.brokerage.test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import it.eng.dome.brokerage.api.AgreementManagementApis;
import it.eng.dome.brokerage.api.fetch.FetchUtils;
import it.eng.dome.tmforum.tmf651.v4.ApiClient;
import it.eng.dome.tmforum.tmf651.v4.ApiException;
import it.eng.dome.tmforum.tmf651.v4.Configuration;
import it.eng.dome.tmforum.tmf651.v4.model.Agreement;
import it.eng.dome.tmforum.tmf651.v4.model.AgreementCreate;
import it.eng.dome.tmforum.tmf651.v4.model.AgreementUpdate;


public class AgreementManagementApisTest {
	
	final static String tmf651CustomerPath = "tmf-api/agreementManagement/v4";
	final static String tmfEndpoint = "https://dome-dev.eng.it";

	@Test
	public void RunTest() {
		
		/**
		 * Get All Agreements
		 */
//		TestGetAllAgreements();
		
		/**
		 * Get Filtered Agreements
		 */
//		TestGetFilteredAgreements();

		/**
		 * Create Agreement
		 */
//		String id = TestCreateAgreement();
//		if (id != null) {
//			Agreement c = TestGetAgreement(id);
//			System.out.println(c.getId() + " " + c.getStatus() + " " + c.getVersion());
//		}
		
		/**
		 * Update Agreement
		 */
//		String id = "urn:ngsi-ld:agreement:1d76d1ee-d7a5-4dff-8369-841ed354ff9e";
//		TestUpdateAgreement(id);

	}
	
	
	protected static void TestGetAllAgreements() {

		ApiClient apiClientTmf651 = Configuration.getDefaultApiClient();
		apiClientTmf651.setBasePath(tmfEndpoint + "/" + tmf651CustomerPath);

		AgreementManagementApis apis = new AgreementManagementApis(apiClientTmf651);		
		AtomicInteger count = new AtomicInteger(0);
		
		FetchUtils.streamAll(
	        apis::listAgreements, 		// method reference
	        null,                       // fields
	        null, 				    	// filter
	        100                         // pageSize
		) 
		.forEach(agreement -> { 
			count.incrementAndGet();
			System.out.println(count + " " + agreement.getId() + " → " + agreement.getName() + " / " + agreement.getStatus());
			}
		);		
		
		System.out.println("Agreement found: " + count);
	}
	
	protected static void TestGetFilteredAgreements() {

		ApiClient apiClientTmf651 = Configuration.getDefaultApiClient();
		apiClientTmf651.setBasePath(tmfEndpoint + "/" + tmf651CustomerPath);

		AgreementManagementApis apis = new AgreementManagementApis(apiClientTmf651);
		AtomicInteger count = new AtomicInteger(0);
		
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("status", "Approved");		
		String fields = "name,version";
		
		FetchUtils.streamAll(
	        apis::listAgreements, 		// method reference
	        fields,                     // fields
	        filter, 			    	// filter
	        100                         // pageSize
		) 
		.forEach(agreement -> { 
			count.incrementAndGet();
			System.out.println(count + " " + agreement.getId() + " → " + agreement.getName() + " / " + agreement.getVersion());
			}
		);		
		
		System.out.println("Agreement filtered found: " + count);

	}

	
	protected static String TestCreateAgreement() {

		ApiClient apiClientTmf651 = Configuration.getDefaultApiClient();
		apiClientTmf651.setBasePath(tmfEndpoint + "/" + tmf651CustomerPath);

		AgreementManagementApis apis = new AgreementManagementApis(apiClientTmf651);

		AgreementCreate ac = new AgreementCreate();
		ac.setName("New Agreement Test");
		ac.setStatus("Approved");
		ac.setVersion("1.0");

		String id = null;
		try {
			id = apis.createAgreement(ac);
		} catch (ApiException e) {
			System.err.println("Error: " + e.getMessage());
		}
		
		return id;
	}
	
	protected static Agreement TestGetAgreement(String id) {

		ApiClient apiClientTmf651 = Configuration.getDefaultApiClient();
		apiClientTmf651.setBasePath(tmfEndpoint + "/" + tmf651CustomerPath);

		AgreementManagementApis apis = new AgreementManagementApis(apiClientTmf651);
		
		try {
			return apis.getAgreement(id, null);
		} catch (ApiException e) {
			System.err.println("Error: " + e.getMessage());
			return null;
		}
	}
	
	protected static void TestUpdateAgreement(String id) {

		ApiClient apiClientTmf651 = Configuration.getDefaultApiClient();
		apiClientTmf651.setBasePath(tmfEndpoint + "/" + tmf651CustomerPath);

		AgreementManagementApis apis = new AgreementManagementApis(apiClientTmf651);
		
		AgreementUpdate au = new AgreementUpdate();
		au.setStatus("Rejected");
		au.setVersion("2.0");
		
		try {
			apis.updateAgreement(id, au);
		} catch (ApiException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
	
}
