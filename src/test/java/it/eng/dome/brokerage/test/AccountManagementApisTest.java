package it.eng.dome.brokerage.test;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import it.eng.dome.brokerage.api.AccountManagementApis;
import it.eng.dome.brokerage.api.page.PaginationUtils;
import it.eng.dome.tmforum.tmf666.v4.ApiClient;
import it.eng.dome.tmforum.tmf666.v4.Configuration;
import it.eng.dome.tmforum.tmf666.v4.model.BillingAccount;


public class AccountManagementApisTest {

	final static String tmf666AccountPath = "tmf-api/accountManagement/v4";
	final static String tmfEndpoint = "https://dome-dev.eng.it";

	public static void main(String[] args) {
				
		/**
		 * PartyAccount
		 */
		//TestGetAllPartyAccounts();

		
		/**
		 * BillingAccount
		 */
		TestGetAllBillingAccounts();
		//TestGetFilteredBillingAccounts();
		TestGetBillAccountById();
				
		
		/**
		 * BillFormat
		 */
		//TestGetAllBillFormats();
		//TestGetBillFormatById();
		
	}
	
	
	protected static void TestGetAllPartyAccounts() {

		ApiClient apiClientTmf666 = Configuration.getDefaultApiClient();
		apiClientTmf666.setBasePath(tmfEndpoint + "/" + tmf666AccountPath);

		AccountManagementApis apis = new AccountManagementApis(apiClientTmf666);			
		AtomicInteger count = new AtomicInteger(0);
		
		PaginationUtils.streamAll(
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
		
		PaginationUtils.streamAll(
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
		
		PaginationUtils.streamAll(
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
		BillingAccount bf = apis.getBillingAccount(id, fields);
		
		if (bf != null) {
			System.out.println("BillingAccount name: " + bf.getName() + " " + bf.getRelatedParty().size());
		}
	}
	
	protected static void TestGetAllBillFormats() {

		ApiClient apiClientTmf666 = Configuration.getDefaultApiClient();
		apiClientTmf666.setBasePath(tmfEndpoint + "/" + tmf666AccountPath);

		AccountManagementApis apis = new AccountManagementApis(apiClientTmf666);
		AtomicInteger count = new AtomicInteger(0);
		
		PaginationUtils.streamAll(
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
	
}
