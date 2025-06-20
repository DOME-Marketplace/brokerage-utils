package it.eng.dome.brokerage.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.eng.dome.brokerage.api.AgreementManagementApis;
import it.eng.dome.tmforum.tmf651.v4.ApiClient;
import it.eng.dome.tmforum.tmf651.v4.Configuration;
import it.eng.dome.tmforum.tmf651.v4.model.Agreement;
import it.eng.dome.tmforum.tmf651.v4.model.AgreementCreate;
import it.eng.dome.tmforum.tmf651.v4.model.AgreementUpdate;


public class AgreementTest {
	
	final static String tmf651CustomerPath = "tmf-api/agreementManagement/v4";
	final static String tmfEndpoint = "https://dome-dev.eng.it";

	public static void main(String[] args) {

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
//		String id = "urn:ngsi-ld:agreement:e07f605e-353c-4f4d-a8ae-aef2244129a2";
//		TestUpdateAgreement(id);

		/**
		 * Get All Agreements
		 */
//		TestGetAllAgreements();
		
		/**
		 * Get Filtered Agreements
		 */
//		TestGetFilteredAgreements();

	}
	
	protected static void TestGetAllAgreements() {

		ApiClient apiClientTmf651 = Configuration.getDefaultApiClient();
		apiClientTmf651.setBasePath(tmfEndpoint + "/" + tmf651CustomerPath);

		AgreementManagementApis apis = new AgreementManagementApis(apiClientTmf651);

		List<Agreement> agreements = apis.getAllAgreements(null, null);

		int count = 0;
		for (Agreement agreement : agreements) {
			System.out.println(	++count + " => " + agreement.getId() + " " + agreement.getStatus() + " " + agreement.getVersion());
		}
	}

	
	protected static String TestCreateAgreement() {

		ApiClient apiClientTmf651 = Configuration.getDefaultApiClient();
		apiClientTmf651.setBasePath(tmfEndpoint + "/" + tmf651CustomerPath);

		AgreementManagementApis apis = new AgreementManagementApis(apiClientTmf651);

		AgreementCreate ac = new AgreementCreate();
		ac.setName("Another agreement");
		ac.setStatus("Approved");
		ac.setVersion("2.1");

		Agreement a = apis.createAgreement(ac);
		
		return a.getId();
	}
	
	protected static Agreement TestGetAgreement(String id) {

		ApiClient apiClientTmf651 = Configuration.getDefaultApiClient();
		apiClientTmf651.setBasePath(tmfEndpoint + "/" + tmf651CustomerPath);

		AgreementManagementApis apis = new AgreementManagementApis(apiClientTmf651);
		
		return apis.getAgreement(id, null);
	}
	
	protected static boolean TestUpdateAgreement(String id) {

		ApiClient apiClientTmf651 = Configuration.getDefaultApiClient();
		apiClientTmf651.setBasePath(tmfEndpoint + "/" + tmf651CustomerPath);

		AgreementManagementApis apis = new AgreementManagementApis(apiClientTmf651);
		
		AgreementUpdate au = new AgreementUpdate();
		au.setStatus("Rejected");
		au.setVersion("3.0");
		
		return apis.updateAgreement(id, au);
	}
	
	protected static void TestGetFilteredAgreements() {

		ApiClient apiClientTmf651 = Configuration.getDefaultApiClient();
		apiClientTmf651.setBasePath(tmfEndpoint + "/" + tmf651CustomerPath);

		AgreementManagementApis apis = new AgreementManagementApis(apiClientTmf651);

		Map<String, String> filter = new HashMap<String, String>();
		filter.put("status", "Approved");
		List<Agreement> agreements = apis.getAllAgreements(null, filter);

		int count = 0;
		for (Agreement agreement : agreements) {
			System.out.println(++count + " => " + agreement.getId() + " " + agreement.getStatus() + " " + agreement.getVersion());
		}
	}
}
