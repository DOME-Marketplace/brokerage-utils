package it.eng.dome.brokerage.test;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import it.eng.dome.brokerage.api.AccountManagementApis;
import it.eng.dome.brokerage.api.fetch.FetchUtils;
import it.eng.dome.tmforum.tmf666.v4.ApiClient;
import it.eng.dome.tmforum.tmf666.v4.ApiException;
import it.eng.dome.tmforum.tmf666.v4.Configuration;
import it.eng.dome.tmforum.tmf666.v4.model.BillFormat;
import it.eng.dome.tmforum.tmf666.v4.model.BillFormatCreate;
import it.eng.dome.tmforum.tmf666.v4.model.BillFormatUpdate;
import it.eng.dome.tmforum.tmf666.v4.model.BillingAccount;


public class AccountManagementApisTest {

	final static String tmf666AccountPath = "tmf-api/accountManagement/v4";
	final static String tmfEndpoint = "https://dome-dev.eng.it";

	@Test
	public void RunTest() {
				
		/**
		 * PartyAccount
		 */
//		TestGetAllPartyAccounts();

		
		/**
		 * BillingAccount
		 */
//		TestGetAllBillingAccounts();
//		TestGetFilteredBillingAccounts();
//		TestGetBillAccountById();
				
		
		/**
		 * BillFormat
		 */
//		TestCreateBillFormat();
		
//		String id = "urn:ngsi-ld:bill-format:c3b254fc-2d6b-42ca-ade1-133d554351bb";
//		TestUpdateBillFormat(id);
//		TestGetAllBillFormats();
//		TestGetBillFormatById(id);
		
	}
	
	
	protected static void TestGetAllPartyAccounts() {

		ApiClient apiClientTmf666 = Configuration.getDefaultApiClient();
		apiClientTmf666.setBasePath(tmfEndpoint + "/" + tmf666AccountPath);

		AccountManagementApis apis = new AccountManagementApis(apiClientTmf666);			
		AtomicInteger count = new AtomicInteger(0);
	
		FetchUtils.streamAll(
	        apis::listPartyAccounts, 	// method reference
	        null,                       // fields
	        null, 				    	// filter
	        100                         // pageSize
		) 
		.forEach(partyAccount -> { 
			count.incrementAndGet();
			System.out.println(count + " " + partyAccount.getId() + " → " + partyAccount.getName() + " / " + partyAccount.getState());
			}
		);		
		
		System.out.println("PartyAccount found: " + count);
	}
	
	protected static void TestGetAllBillingAccounts() {

		ApiClient apiClientTmf666 = Configuration.getDefaultApiClient();
		apiClientTmf666.setBasePath(tmfEndpoint + "/" + tmf666AccountPath);

		AccountManagementApis apis = new AccountManagementApis(apiClientTmf666);		
		AtomicInteger count = new AtomicInteger(0);
		
		FetchUtils.streamAll(
	        apis::listBillingAccounts, 	// method reference
	        null,                       // fields
	        null, 				    	// filter
	        100                         // pageSize
		) 
		.forEach(billingAccount -> { 
			count.incrementAndGet();
			System.out.println(count + " " + billingAccount.getId() + " → " + billingAccount.getName());
			}
		);		
		
		System.out.println("BillingAccount found: " + count);
	}
	
	
	protected static void TestGetFilteredBillingAccounts() {

		ApiClient apiClientTmf666 = Configuration.getDefaultApiClient();
		apiClientTmf666.setBasePath(tmfEndpoint + "/" + tmf666AccountPath);

		AccountManagementApis apis = new AccountManagementApis(apiClientTmf666);		
		AtomicInteger count = new AtomicInteger(0);
		
		FetchUtils.streamAll(
	        apis::listBillingAccounts, 	// method reference
	        null,                       // fields
	        Map.of("relatedParty.id", "urn:ngsi-ld:organization:c9fcb8fa-3d7b-4fb8-b999-01ca987ceb16"), // filter
	        100                         // pageSize
		)
		.forEach(billingAccount -> { 
			count.incrementAndGet();
			System.out.println(count + " " + billingAccount.getId() + " → " + billingAccount.getName());
			}
		);		
		
		System.out.println("BillingAccount found: " + count);
	}

	
	protected static void TestGetBillAccountById() {

		ApiClient apiClientTmf666 = Configuration.getDefaultApiClient();
		apiClientTmf666.setBasePath(tmfEndpoint + "/" + tmf666AccountPath);

		AccountManagementApis apis = new AccountManagementApis(apiClientTmf666);

		String id = "urn:ngsi-ld:billing-account:3bf025cb-1b58-48be-b0ae-bb0967d09d3b";
		String fields = "name,relatedParty";
		try {
			BillingAccount bf = apis.getBillingAccount(id, fields);
			
			if (bf != null) {
				System.out.println("BillingAccount name: " + bf.getName() + " " + bf.getRelatedParty().size());
			}
		} catch (ApiException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	protected static void TestGetAllBillFormats() {

		ApiClient apiClientTmf666 = Configuration.getDefaultApiClient();
		apiClientTmf666.setBasePath(tmfEndpoint + "/" + tmf666AccountPath);

		AccountManagementApis apis = new AccountManagementApis(apiClientTmf666);
		AtomicInteger count = new AtomicInteger(0);
		
		FetchUtils.streamAll(
	        apis::listBillFormats, 		// method reference
	        null,                       // fields
	        null, 				    	// filter
	        100                         // pageSize
		) 
		.forEach(billFormat -> { 
			count.incrementAndGet();
			System.out.println(count + " " + billFormat.getId() + " → " + billFormat.getName());
			}
		);		
		
		System.out.println("BillFormat found: " + count);
	}
	
	protected static void TestCreateBillFormat() {

		ApiClient apiClientTmf666 = Configuration.getDefaultApiClient();
		apiClientTmf666.setBasePath(tmfEndpoint + "/" + tmf666AccountPath);

		AccountManagementApis apis = new AccountManagementApis(apiClientTmf666);
		
		BillFormatCreate bc = new BillFormatCreate();
		bc.setName("Test for bill format");
		bc.setDescription("This is an example using Java Test!");
		
		try {
			String id = apis.createBillFormat(bc);
			System.out.println("BillFormat ID: " + id);
			
		} catch (ApiException e) {
			System.err.println("Error: " + e.getMessage());
		}
		
	}
	
	protected static void TestUpdateBillFormat(String id) {

		ApiClient apiClientTmf666 = Configuration.getDefaultApiClient();
		apiClientTmf666.setBasePath(tmfEndpoint + "/" + tmf666AccountPath);

		AccountManagementApis apis = new AccountManagementApis(apiClientTmf666);
		
		BillFormatUpdate bfu = new BillFormatUpdate();
		bfu.setDescription("Bill Format update via Java API just now!");
		try {	
			apis.updateBillFormat(id, bfu);
		} catch (ApiException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	
	protected static void TestGetBillFormatById(String id) {

		ApiClient apiClientTmf666 = Configuration.getDefaultApiClient();
		apiClientTmf666.setBasePath(tmfEndpoint + "/" + tmf666AccountPath);

		AccountManagementApis apis = new AccountManagementApis(apiClientTmf666);

		try {	
			BillFormat bf = apis.getBillFormat(id, null);
			if (bf != null) {
				System.out.println("BillingAccount name: " + bf.getName() + " " + bf.getDescription());
			}
		} catch (ApiException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
}
